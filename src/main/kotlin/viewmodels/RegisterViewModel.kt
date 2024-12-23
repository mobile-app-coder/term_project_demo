package viewmodels

import androidx.compose.runtime.mutableStateOf
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


class RegisterViewModel : ViewModel() {
    var name = mutableStateOf("")

    var dateOfBirth = mutableStateOf("")

    var address = mutableStateOf("")

    var email = mutableStateOf("")

    var phone = mutableStateOf("")
    var login = mutableStateOf("")

    var password = mutableStateOf("")

    var passwordConfirmation = mutableStateOf("")

    fun register(
        navigator: Navigator
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                name = name.value,
                date = dateOfBirth.value,
                address = address.value,
                email = email.value,
                login = login.value,
                phone = phone.value,
                password = password.value
            )
            val message = UserGsonConverter.toJson(user)
            Network.sendMessage(
                "register:$message"
            )
            delay(100)
            val response = Network.getMessage()
            println(response)
        }
    }

    init {
        val user = UserGenerator.generateUser()
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            // Update UI fields with user data one by one with a short delay:
            name.value = user.name
            delay(500) // Short delay between field updates for visual feedback
            dateOfBirth.value = user.date
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

