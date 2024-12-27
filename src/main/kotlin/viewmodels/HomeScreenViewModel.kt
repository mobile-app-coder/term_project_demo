package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.AccountGsonConverter
import models.UserGsonConverter
import network.Network
import server.model.AccountTransfer
import server.model.AccountTransferGsonConverter


class HomeScreenViewModel(private val userId: String) : ViewModel() {
    var balance = mutableStateOf("")

    val searchText = mutableStateOf("")
    var accountId = 0
    var showNotifications = mutableStateOf(false)
    var showSettings = mutableStateOf(false)


    private var _transaction = mutableStateListOf<AccountTransfer>()
    val transfers = _transaction


    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserData()
            } catch (e: Exception) {
                println("Error in init block: $e")
            }
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
                senderAccountId = accountId,
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
                val response = Network.getMessage()
                if (response != null) {
                    if (response.startsWith("ok")) {
                        delay(200)
                        getUserData()
                        delay(200)
                        getRecentTransaction()
                        transferDestination = ""
                        transferDescription = ""
                        transferMoney = ""
                        passportId = ""
                    }
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }


    private suspend fun getRecentTransaction() {
        try {
            // Send request
            Network.sendMessage("recenttransaction:$accountId")
            delay(100)
            val message = Network.getMessage()

            if (message != null && message.startsWith("ok")) {
                val transferlist = message.split("|")[1]
                val transferlistFromJson = AccountTransferGsonConverter.fromJsonList(transferlist)

                if (transferlistFromJson != null) {
                    withContext(Dispatchers.Main) {
                        _transaction.clear()
                        _transaction.addAll(transferlistFromJson)
                    }
                }
            }
        } catch (e: Exception) {
            println("Error at getting transaction history: $e")
        }
    }

    private suspend fun getUserData() {
        try {
            val request = "userdata:$userId"
            Network.sendMessage(request)
            delay(200) // Allow server response
            val response = Network.getMessage()

            if (response != null) {
                val params = response.split("|")
                val user = UserGsonConverter.fromJson(params[1])
                val accounts = AccountGsonConverter.fromJsonList(params[2])


                accountId = accounts?.get(0)?.accountId ?: 0
                balance.value = accounts?.get(0)?.balance.toString()


                //getRecentTransaction()
            }
        } catch (e: Exception) {
            println("Error at getting user data: $e")
        }
    }


}