package ghar.learn.blueapp.domain.chat

/**  typealias in original code as 'BluetoothDeviceDomain */

//typealias MyBluetoothDeviceDomain = MyBluetoothDevice     // not using
data class MyBluetoothDevice (
    val name: String?,
    val macAddress: String          // physical device of Bluetooth-devices
)