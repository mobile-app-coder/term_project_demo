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
            Text(name)
            Text(moneyAmount)
            Text(applicationType)
        }
        Text(date.toString())
        Text(
            status, color = Color.White, modifier = Modifier.size(width = 100.dp, height = 30.dp).background(
                color = when (status) {
                    "Approved" -> Color(0xff85c1e9)
                    "Waiting" -> Color.Green
                    else -> Color.Red
                }
            ).padding(all = 4.dp).align(alignment = Alignment.CenterVertically)
        )
    }
}
