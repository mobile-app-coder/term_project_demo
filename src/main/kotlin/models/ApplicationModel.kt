package models

import java.time.LocalDate

data class ApplicationModel(
    val name:String,
    val moneyAmount:String,
    val date:LocalDate,
    val type: ServiceType


)

enum class ServiceType(name: String) {
    Loan("Loan"),
    Deposit("Deposit"),
    Withdraw("Withdraw"),
}

enum class Status {
    Approved,
    Rejected,
    Waiting
}