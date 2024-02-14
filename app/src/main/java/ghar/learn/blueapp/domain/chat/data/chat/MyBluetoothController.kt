package ghar.learn.blueapp.domain.chat.data.chat

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import ghar.learn.blueapp.domain.chat.IMyBluetoothController
import ghar.learn.blueapp.domain.chat.MyBluetoothDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
class MyBluetoothController(private val context: Context
) : IMyBluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    /** provides all scanned, paired devices info, among other set of info */
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _scannedDevices = MutableStateFlow<List<MyBluetoothDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<MyBluetoothDevice>> = _scannedDevices.asStateFlow()

    private val _pairedDevices =  MutableStateFlow<List<MyBluetoothDevice>>(emptyList())
    override val pairedDevices: StateFlow<List<MyBluetoothDevice>>
        get() = _pairedDevices.asStateFlow()

    private val foundBLDeviceFoundReceiver = BLDeviceFoundReceiver { device ->
        _scannedDevices.update {devices->
            val newDevice = device.toMyBluetoothDevice()
            if(newDevice in devices) devices else devices + newDevice   // add only if new device is not already in the list

        }
    }
    init {
        updatePairDevices()
        bluetoothAdapter?.startDiscovery()
    }
    override fun startDiscover() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        // Register the BroadcastReceiver
        context.registerReceiver(
            foundBLDeviceFoundReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairDevices()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscover() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        } else {
            bluetoothAdapter?.cancelDiscovery()
        }
    }

    override fun release() {
        context.unregisterReceiver(foundBLDeviceFoundReceiver)          // un-register the receiver
    }

    private fun updatePairDevices() {
        if(!hasPermission(android.Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map {it.toMyBluetoothDevice() }
            ?.also { devides ->
                _pairedDevices.update { devides }
            }
    }

    private fun hasPermission(permission: String) : Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}