package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {

    var balance = mutableStateOf("")

    val searchText = mutableStateOf("")

    var showNotifications  = mutableStateOf(false)
    var showSettings =  mutableStateOf(false)

    init {
        //some function to suspend
        balance.value = "1000";
    }
}