package viewmodels



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var login =
        mutableStateOf("")

    var password =
        mutableStateOf("")


}
