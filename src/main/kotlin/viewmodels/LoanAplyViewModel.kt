package viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoanApplyViewModel : ViewModel() {
    var name = mutableStateOf("")
    var incom = mutableStateOf("")
    var salary = mutableStateOf("")
    var amount = mutableStateOf("")
    var loanPurpose = mutableStateOf("")
    var carType = mutableStateOf("")
    var homeLocation = mutableStateOf("")
}