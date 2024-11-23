package viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class HomeScreenViewModel : ViewModel() {

    var balance = mutableStateOf("")

    val searchText = mutableStateOf("")

    var showNotifications = mutableStateOf(false)
    var showSettings = mutableStateOf(false)

    private val _items = mutableStateListOf<String>()
    val items: SnapshotStateList<String> = _items

    fun addItem(item: String) {
        if (item.isNotBlank()) {
            _items.add(item)
        }
    }

    fun fetchItemsFromServer() {
        viewModelScope.launch {
            // Simulating server delay or fetching items
            val serverItems = listOf("Server Item 1", "Server Item 2")
            _items.clear()
            _items.addAll(serverItems)
        }
    }

    fun addMessage(message: String) {
        message.plus(message)
    }

}