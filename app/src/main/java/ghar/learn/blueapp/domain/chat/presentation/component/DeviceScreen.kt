package ghar.learn.blueapp.domain.chat.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ghar.learn.blueapp.domain.chat.MyBluetoothDevice
import ghar.learn.blueapp.domain.chat.presentation.MyBluetoothUIState

@Composable
fun DeviceScreen(
    state: State<MyBluetoothUIState>,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

//        BluetoothDeviceList(pairedDevices = , scannedDevices = , onClick = )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onStartScan) {
                Text(text = "Start scan")
            }
            Button(onClick = onStopScan) {
                Text(text = "Stop scan")
            }
        }
    }

}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<MyBluetoothDevice>,
    scannedDevices: List<MyBluetoothDevice>,
    onClick: (MyBluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
    ) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->        // display Paired - devices
            Text(
                text = device.name ?: "No Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            )
        }
        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->       // display Scanned - devices
            Text(
                text = device.name ?: "No Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            )
        }
    }

}