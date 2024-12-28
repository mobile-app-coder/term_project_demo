package screens.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate


@Composable
fun NotificationPanel(onClose: () -> Unit) {
    Box(
        Modifier.fillMaxHeight().width(300.dp)
            .background(Color(0xFF1E88E5), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)).padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Notifications", color = Color.White, style = MaterialTheme.typography.h6)
            Button(onClick = onClose, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Close", color = Color(0xFF1E88E5))
            }
        }
    }
}

@Composable
fun SettingsPanel(onClose: () -> Unit) {
    Box(
        Modifier.fillMaxHeight().width(300.dp)
            .background(Color(0xFF4CAF50), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)).padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", color = Color.White, style = MaterialTheme.typography.h6)
            Row {

            }
            Button(onClick = onClose, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Close", color = Color(0xFF4CAF50))
            }
        }
    }


}



@Composable
fun TotalBalanceAndCurrency(balance: String) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Total Balance", fontSize = 18.sp, color = Color.Gray)
            Text("$ $balance", fontSize = 24.sp, color = Color.Black)
        }
        Column {
            Text("Currency Prices (USD)", fontSize = 18.sp, color = Color.Gray)
            Text("1 USD = 1.2 EUR", fontSize = 14.sp, color = Color.Black)
            Text("1 USD = 110 JPY", fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun ServiceCard(title: String, details: String, modifier: Modifier) {
    Box(
        modifier
    ) {
        Column {
            Text(text = title, color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text(text = details, color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun CreditCard(name: String, cardNumber: String, balance: String, color: Color) {
    Box(
        Modifier.size(250.dp).aspectRatio(3 / 1.5f).background(color, shape = RoundedCornerShape(16.dp)).padding(16.dp)
    ) {
        Column {
            Text(name, color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Text(cardNumber, color = Color.White, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            Text("Balance: $balance", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun TransactionList(messages: StateFlow<List<String>>) {
    Column() {
        TransactionItem("Withdrawal", "-$500", "2024-11-15", Color.Red)
        TransactionItem("Deposit", "+$2,000", "2024-11-12", Color.Green)
        TransactionItem("Loan Payment", "-$1,000", "2024-11-10", Color.Red)
    }
}

@Composable
fun TransactionItem(description: String, amount: String, date: String, amountColor: Color) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(description, fontSize = 16.sp, color = Color.Black)
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
        Text(amount, fontSize = 16.sp, color = amountColor)
    }
}

@Composable
fun ApplicationItem(name: String, applicationType: String, status: String, date: LocalDate, moneyAmount: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
    ) {
        Column {
            Text(name, style = MaterialTheme.typography.body1)
            Text(moneyAmount, style = MaterialTheme.typography.body2)
            Text(applicationType, style = MaterialTheme.typography.body2)
        }
        Text(date.toString(), style = MaterialTheme.typography.body2)
        Text(
            status,
            color = Color.White,
            modifier = Modifier
                .size(width = 100.dp, height = 30.dp)
                .background(
                    color = when (status) {
                        "Approved" -> Color(0xff85c1e9) // Light Blue
                        "Waiting" -> Color.Green // Green for Waiting
                        else -> Color.Red // Red for Rejected
                    }
                )
                .padding(all = 4.dp)
                .align(alignment = Alignment.CenterVertically),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun ApplicationList(modifier: Modifier) {
    val sampleApplications = listOf(
        ApplicationData("John Doe", "Car Loan", "Approved", LocalDate.now().minusDays(1), "$50,000"),
        ApplicationData("Jane Smith", "Home Loan", "Waiting", LocalDate.now().minusDays(2), "$250,000"),
        ApplicationData("Emily Green", "Business Loan", "Rejected", LocalDate.now().minusDays(3), "$100,000"),
        ApplicationData("Michael Brown", "Personal Loan", "Approved", LocalDate.now().minusDays(4), "$30,000"),
        ApplicationData("Chris Johnson", "Student Loan", "Waiting", LocalDate.now().minusDays(5), "$20,000"),
        ApplicationData("Sarah Lee", "Car Loan", "Approved", LocalDate.now().minusDays(6), "$40,000"),
        ApplicationData("David Wilson", "Business Loan", "Rejected", LocalDate.now().minusDays(7), "$200,000"),
        ApplicationData("Sophia Martinez", "Home Loan", "Waiting", LocalDate.now().minusDays(8), "$300,000"),
        ApplicationData("Liam Harris", "Personal Loan", "Approved", LocalDate.now().minusDays(9), "$15,000"),
        ApplicationData("Olivia Clark", "Car Loan", "Rejected", LocalDate.now().minusDays(10), "$25,000"),
        ApplicationData("Daniel Lewis", "Student Loan", "Approved", LocalDate.now().minusDays(11), "$5,000"),
        ApplicationData("Charlotte Walker", "Home Loan", "Waiting", LocalDate.now().minusDays(12), "$350,000"),
        ApplicationData("Amelia Hall", "Car Loan", "Approved", LocalDate.now().minusDays(13), "$60,000"),
        ApplicationData("Ethan Allen", "Business Loan", "Rejected", LocalDate.now().minusDays(14), "$150,000"),
        ApplicationData("Mason Young", "Personal Loan", "Waiting", LocalDate.now().minusDays(15), "$10,000"),
        ApplicationData("Isabella King", "Car Loan", "Approved", LocalDate.now().minusDays(16), "$45,000"),
        ApplicationData("Alexander Scott", "Business Loan", "Rejected", LocalDate.now().minusDays(17), "$500,000"),
        ApplicationData("Lucas Green", "Student Loan", "Waiting", LocalDate.now().minusDays(18), "$8,000"),
        ApplicationData("Zoe Adams", "Home Loan", "Approved", LocalDate.now().minusDays(19), "$200,000")
    )

    Column(modifier = modifier) {
        sampleApplications.forEach {
            ApplicationItem(
                name = it.name,
                applicationType = it.applicationType,
                status = it.status,
                date = it.date,
                moneyAmount = it.moneyAmount
            )
        }
    }
}

data class ApplicationData(
    val name: String,
    val applicationType: String,
    val status: String,
    val date: LocalDate,
    val moneyAmount: String
)

