package ghar.learn.blueapp.domain.chat.presentation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ghar.learn.blueapp.domain.chat.IMyBluetoothController
import ghar.learn.blueapp.domain.chat.data.chat.MyBluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyBluetoothViewModel @Inject constructor(
    private val iMyBluetoothController: IMyBluetoothController
): ViewModel() {

    private val _state = MutableStateFlow(MyBluetoothUIState())
    val state = combine(
        iMyBluetoothController.scannedDevices,
        iMyBluetoothController.pairedDevices,
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(                                               // converts flow to StateFlow
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    // scan start
    fun startScan() {
        iMyBluetoothController.startDiscover()
    }

    fun stopScan() {
        iMyBluetoothController.stopDiscover()
    }

}