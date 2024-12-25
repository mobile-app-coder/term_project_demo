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
import models.User
import models.UserGenerator
import models.UserGsonConverter
import network.Network
import screens.LoginScreen


class RegisterViewModel : ViewModel() {
    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var passportId by mutableStateOf("")
    var dateOfBirth = mutableStateOf("")
    var address = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var login = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordConfirmation = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    fun register(navigator: Navigator) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            errorMessage.value = ""
            try {
                val user = User(
                    firstName = firstName.value,
                    lastName = lastName.value,
                    passportId = passportId,
                    dateOfBirth = dateOfBirth.value,
                    address = address.value,
                    email = email.value,
                    login = login.value,
                    phone = phone.value,
                    password = password.value
                )
                val message = UserGsonConverter.toJson(user)
                Network.sendMessage("register:$message")
                delay(100)
                val response = Network.getMessage()
                println("log at registering: $response")
                if (response == "ok") {
                    navigator.replace(LoginScreen())
                    isLoading.value = false
                } else {
                    isLoading.value = false
                    errorMessage.value = response ?: "Error occurred" // Display error message from server
                }
            } catch (e: Exception) {
                errorMessage.value = "An error occurred: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }

    init {
        val user = UserGenerator.generateUser()
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            // Update UI fields with user data one by one with a short delay:
            firstName.value = user.firstName
            delay(500) // Short delay between field updates for visual feedback
            lastName.value = user.lastName
            delay(500)
            dateOfBirth.value = user.dateOfBirth
            delay(500)
            address.value = user.address
            delay(500)
            email.value = user.email
            delay(500)
            phone.value = user.phone
            delay(500)
            login.value = user.login
            delay(500)
            password.value = user.password
            delay(500)
            passwordConfirmation.value = user.password
        }

    }
}

