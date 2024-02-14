package ghar.learn.blueapp.domain.chat.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import ghar.learn.blueapp.domain.chat.MyBluetoothDevice

/**
 * This class maps the customized Bluetooth-Device to library-provided
 * Bluetooth class
 */
@SuppressLint("MissingPermission")
fun BluetoothDevice.toMyBluetoothDevice() : MyBluetoothDevice {
    return MyBluetoothDevice(
        name = name,
        macAddress = address

    )
}