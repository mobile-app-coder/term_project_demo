package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.LoginModel
import models.LoginModelGsonConverter
import network.Network
import screens.CreateBankAccountScreen


class LoginViewModel : ViewModel() {

    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun login(navigator: Navigator) {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val message = LoginModelGsonConverter.toJson(LoginModel(login, password))
            if (message != null) {
                Network.sendMessage("login:$message")
                delay(100)
                val response = Network.getMessage()?.split(":")
                val loginResult = response?.get(0)
                if (loginResult == "ok") {
                    isLoading = false
                    navigator.replace(CreateBankAccountScreen(response[1]))
                } else {
                    errorMessage = loginResult ?: "Something went wrong check you username and password"
                }
            }
        }
        isLoading = false
    }

}

