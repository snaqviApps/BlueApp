package ghar.learn.blueapp.domain.chat.data.chat

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable

class BLDeviceFoundReceiver(
    private val onDeviceFound : (BluetoothDevice) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            BluetoothDevice.ACTION_FOUND -> {
                val blDevice: Array<out Parcelable>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableArrayExtra(
                        BluetoothDevice.EXTRA_NAME,
                        BluetoothDevice::class.java
                    )
                } else {
                    intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_NAME)
                }
                blDevice?.let { onDeviceFound }             // device found
            }
        }
    }
}