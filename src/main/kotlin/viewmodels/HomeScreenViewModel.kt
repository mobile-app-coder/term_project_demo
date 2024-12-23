package viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.Network


class HomeScreenViewModel(private val userId: String) : ViewModel() {
    var balance = mutableStateOf("")

    val searchText = mutableStateOf("")

    var showNotifications = mutableStateOf(false)
    var showSettings = mutableStateOf(false)

    private val _items = mutableStateListOf<String>()
    val items: SnapshotStateList<String> = _items


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserData()
        }
    }


    private suspend fun getUserData() {
        val request = "userdata:$userId"
        Network.sendMessage(request)
        delay(100)
        val response = Network.getMessage()

        if (response != null) {
            println(response)
        }
    }

}