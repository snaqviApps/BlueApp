package ghar.learn.blueapp.domain.chat.data.chat

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
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

    init {
        updatePairDevices()
        bluetoothAdapter?.startDiscovery()
    }
    override fun startDiscover() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        updatePairDevices()
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscover() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
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