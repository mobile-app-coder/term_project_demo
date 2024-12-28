package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import java.time.LocalDate

// Deposit Screen
class DepositScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Screen Title
            Text(
                "Deposit Money",
                style = TextStyle(fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            )

            // Deposit Amount Input Section
            DepositAmountSection()

            // Account Selection Dropdown
            AccountSelectionSection()

            // Submit Deposit Button
            SubmitDepositButton()

            // Recent Deposits Section
            RecentDepositsSection()
        }
    }

    // Deposit Amount Input Section
    @Composable
    private fun DepositAmountSection() {
        var depositAmount by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Enter Deposit Amount", style = TextStyle(fontSize = 18.sp, color = Color.Gray))
            Spacer(modifier = Modifier.height(8.dp))

            // Stylish input field for deposit amount
            TextField(
                value = depositAmount,
                onValueChange = { depositAmount = it },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(12.dp),
            )
        }
    }

    // Account Selection Dropdown Section
    @Composable
    private fun AccountSelectionSection() {
        var selectedAccount by remember { mutableStateOf("Account 1") }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Select Account", style = TextStyle(fontSize = 18.sp, color = Color.Gray))
            Spacer(modifier = Modifier.height(8.dp))

            // Stylish Dropdown for Account selection
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedAccount, style = TextStyle(fontSize = 16.sp, color = Color.Black))
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon", tint = Color.Gray)
                }
            }
        }
    }

    // Submit Deposit Button Section
    @Composable
    private fun SubmitDepositButton() {
        Button(
            onClick = { /* Process deposit */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Submit Deposit", color = Color.White, style = TextStyle(fontSize = 18.sp))
        }
    }

    // Recent Deposits Section
    @Composable
    private fun RecentDepositsSection() {
        Text(
            "Recent Deposits",
            style = TextStyle(fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        )

        // Sample data for recent deposits
        val recentDeposits = listOf(
            Deposit("Account 1", 500.0, LocalDate.now(), "Completed", 30.0),
            Deposit("Account 2", 1200.0, LocalDate.now().minusDays(1), "Pending", 0.0),
            Deposit("Account 1", 300.0, LocalDate.now().minusDays(2), "Completed", 20.0),
            Deposit("Account 3", 1500.0, LocalDate.now().minusDays(3), "Failed", 0.0)
        )

        // Display recent deposits in a list
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(recentDeposits.size) { index ->
                DepositItem(deposit = recentDeposits[index])
            }
        }
    }

    // Deposit Item Display
    @Composable
    private fun DepositItem(deposit: Deposit) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        deposit.account,
                        style = TextStyle(fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Amount: \$${deposit.amount}", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
                    Text("Date: ${deposit.date}", style = TextStyle(fontSize = 12.sp, color = Color.Gray))
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Status: ${deposit.status}",
                        style = TextStyle(fontSize = 14.sp, color = getStatusColor(deposit.status))
                    )
                    Text("Income: \$${deposit.income}", style = TextStyle(fontSize = 14.sp, color = Color.Green))
                }
            }
        }
    }

    // Status Color Based on Deposit Status
    private fun getStatusColor(status: String): Color {
        return when (status) {
            "Completed" -> Color.Green
            "Pending" -> Color.Yellow
            "Failed" -> Color.Red
            else -> Color.Gray
        }
    }

    data class Deposit(
        val account: String,
        val amount: Double,
        val date: LocalDate,
        val status: String,
        val income: Double
    )
}

