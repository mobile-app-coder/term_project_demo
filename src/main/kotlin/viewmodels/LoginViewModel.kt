package viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.User


class LoginViewModel : ViewModel() {
    var name =
        mutableStateOf("")

    var dateOfBirth =
        mutableStateOf("")

    var address =
        mutableStateOf("")

    var email =
        mutableStateOf("")

    var phone =
        mutableStateOf("")
    var login =
        mutableStateOf("")

    var password =
        mutableStateOf("")

    var passwordConfirmation =
        mutableStateOf("")

    fun register(
        name: String,
        date: String,
        address: String,
        email: String,
        phone: String,
        login: String,
        password: String
    ): User {
        return User(
            name = name,
            date = date,
            address = address,
            email = email,
            phone = phone,
            login = login,
            password = password
        )
    }
}

