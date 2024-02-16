package ghar.learn.blueapp.domain.chat.presentation

import ghar.learn.blueapp.domain.chat.MyBluetoothDevice


data class MyBluetoothUIState(
    val scannedDevices: List<MyBluetoothDevice> = emptyList(),
    val pairedDevices: List<MyBluetoothDevice> = emptyList()
)
