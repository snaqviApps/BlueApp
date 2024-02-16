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

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update { devices->
            val newDevices = device.toMyBluetoothDevice()
            if(newDevices in devices) devices else devices + newDevices
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
        context.registerReceiver(                  // Intent-Action registration
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)  // Action
        )
        updatePairDevices()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscover() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    private fun updatePairDevices() {
        if(!hasPermission(android.Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map {it.toMyBluetoothDevice() }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }

    private fun hasPermission(permission: String) : Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}