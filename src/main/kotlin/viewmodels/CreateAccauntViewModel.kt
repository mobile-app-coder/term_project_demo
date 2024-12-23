package viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.AccountType
import models.AccountTypeGsonConverter
import network.Network

class CreateAccountViewModel(private val userId: String) : ViewModel() {

    private var _types = mutableStateListOf<AccountType>()
    var types: SnapshotStateList<AccountType> = _types

    suspend fun getAccountTypes() {
        val request = "accounttype"
        try {
            Network.sendMessage(request)
            val response = Network.getMessage()
            if (response != null) {

                println(response)
                val accountTypes = AccountTypeGsonConverter.fromJsonList(response)
                if (accountTypes != null) {
                    _types.clear()  // Clear existing data
                    _types.addAll(accountTypes)  // Add new data
                }
            }
        } catch (e: Exception) {
            // Handle exceptions such as network failure
            println("Error fetching account types: ${e.message}")
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountTypes()
        }
    }

}