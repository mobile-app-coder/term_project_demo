package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.AccountGsonConverter
import models.UserGsonConverter
import network.Network
import server.model.AccountTransfer
import server.model.AccountTransferGsonConverter


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


    //transfer variables
    var transferDestination by mutableStateOf("")
    var transferMoney by mutableStateOf("")
    var transferDescription by mutableStateOf("")
    var passportId by mutableStateOf("")

    //transfer function
    fun transfer() {
        val transfer =
            AccountTransfer(
                senderAccountId = 16.toInt(),
                receiverAccountId = transferDestination.toInt(),
                amount = transferMoney.toDouble(),
                description = transferDescription
            )
        val transferJson = AccountTransferGsonConverter.toJson(transfer)
        val request = "transaction:$transferJson"
        try {
            viewModelScope.launch(Dispatchers.IO) {
                //transfer variables
                Network.sendMessage(request)
                getUserData()
            }
        } catch (e: Exception) {

        }
    }

    private suspend fun getUserData() {
        val request = "userdata:$userId"
        Network.sendMessage(request)
        delay(200)
        val response = Network.getMessage()

        if (response != null) {
            println("user$response")
            val params = response.split("|")
            var user = UserGsonConverter.fromJson(params[1])
            println(params[2])
            val accounts = AccountGsonConverter.fromJsonList(params[2])
            balance.value = accounts?.get(0)?.balance.toString()
        }
    }

}