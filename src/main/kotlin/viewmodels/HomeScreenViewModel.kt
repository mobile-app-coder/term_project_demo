package viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {
    val searchText = mutableStateOf("")
}