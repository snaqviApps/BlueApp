package ghar.learn.blueapp.domain.chat

import kotlinx.coroutines.flow.StateFlow

interface IMyBluetoothController {
    val scannedDevices: StateFlow<List<MyBluetoothDevice>>
    val pairedDevices: StateFlow<List<MyBluetoothDevice>>

    fun startDiscover()
    fun stopDiscover()

    fun release()
}