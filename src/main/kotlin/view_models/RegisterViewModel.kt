package view_models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel


class RegisterViewModel : ViewModel() {
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
}
