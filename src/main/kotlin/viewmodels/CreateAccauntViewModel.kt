package viewmodel


import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.*
import network.Network
import screens.HomeScreen


class CreateAccountViewModel(private val userId: String) : ViewModel() {

    private var _types = mutableStateListOf<AccountType>()
    var types: SnapshotStateList<AccountType> = _types

    private var selectedTypeId = -1

    var selectedTypeName by mutableStateOf("")

    var currencies = mutableStateListOf(
        Currency(1, "USD", "United States Dollar", "$", 1.0),
        Currency(2, "EUR", "Euro", "€", 0.85),
        Currency(3, "GBP", "British Pound", "£", 0.75)
    )

    // Selected currency state
    var selectedCurrency by mutableStateOf(currencies[0]) // Default to USD
    var accountHolderName by mutableStateOf("")
    var initialDeposit by mutableStateOf("")

    // Function to select currency
    fun selectCurrency(currency: Currency) {
        selectedCurrency = currency
    }

    fun selectType(type: AccountType) {
        selectedTypeId = type.id
        selectedTypeName = type.name
    }

    private suspend fun getCurrencies() {
        val request = "currency"
        try {
            Network.sendMessage(request)
            delay(100)
            val response = Network.getMessage()
            val _currencies = response?.let { CurrencyGsonConverter.fromJsonList(it) }
            if (_currencies != null) {
                currencies = _currencies.toMutableStateList()
            }


        } catch (e: Exception) {

        }
    }

    private suspend fun getAccountTypes() {
        val request = "accounttype"
        try {
            Network.sendMessage(request)
            delay(1000)
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

    fun crateAccount(navigator: Navigator) {
        val account = BankAccount(
            accountHolder = accountHolderName,
            accountTypeId = selectedTypeId,
            currencyId = selectedCurrency.currencyId,
            balance = initialDeposit.toDouble(),
            userId = userId
        )

        try {
            val parameter = AccountGsonConverter.toJson(account)
            val message = "createaccount:$parameter"

            viewModelScope.launch(Dispatchers.IO) {
                Network.sendMessage(message)

                delay(100)
                val response = Network.getMessage()
                println(response)

                val params = response?.split("|")
                if ((params?.get(0) ?: "") == "ok") {
                    println(params?.get(1))
                    val bankAccount = AccountGsonConverter.fromJson(params?.get(1) ?: "")
                    navigator.replace(HomeScreen(userId))
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountTypes()
            getCurrencies()
        }
    }
}