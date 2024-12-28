package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.font.FontWeight
import java.time.LocalDate

// Transaction History Screen
class TransactionHistoryScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Screen Title
            Text("Transaction History", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

            // Filter Dropdown
            TransactionFilterSection()

            Spacer(Modifier.height(16.dp))

            // Transaction List Section
            TransactionListSection()
        }
    }

    // Filter Dropdown Section
    @Composable
    private fun TransactionFilterSection() {
        var selectedFilter by remember { mutableStateOf("All") }
        val filterOptions = listOf("All", "Completed", "Pending", "Failed")

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Filter Transactions", style = TextStyle(fontSize = 18.sp, color = Color.Gray))
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown for filter options
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
                    Text(selectedFilter, style = TextStyle(fontSize = 16.sp, color = Color.Black))
                    Icon(Icons.Filled.FilterList, contentDescription = "Filter Icon", tint = Color.Gray)
                }
            }

            // Dropdown menu for filter options
            DropdownMenu(
                expanded = true,
                onDismissRequest = {},
            ) {
                filterOptions.forEach { option ->
                    DropdownMenuItem(onClick = { selectedFilter = option }) {
                        Text(text = option)
                    }
                }
            }
        }
    }

    // Transaction List Section
    @Composable
    private fun TransactionListSection() {
        Text("Recent Transactions", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))

        // Sample data for transactions
        val transactions = listOf(
            Transaction("Account 1", 200.0, LocalDate.now(), "Completed"),
            Transaction("Account 2", 1500.0, LocalDate.now().minusDays(1), "Pending"),
            Transaction("Account 1", 450.0, LocalDate.now().minusDays(2), "Failed"),
            Transaction("Account 3", 900.0, LocalDate.now().minusDays(3), "Completed")
        )

        // Display recent transactions in a list
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(transactions.size) { index ->
                TransactionItem(transaction = transactions[index])
            }
        }
    }

    // Transaction Item Display
    @Composable
    private fun TransactionItem(transaction: Transaction) {
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
                    Text(transaction.account, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Amount: \$${transaction.amount}", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
                    Text("Date: ${transaction.date}", style = TextStyle(fontSize = 12.sp, color = Color.Gray))
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Status: ${transaction.status}", style = TextStyle(fontSize = 14.sp, color = getStatusColor(transaction.status)))
                }
            }
        }
    }

    // Status Color Based on Transaction Status
    private fun getStatusColor(status: String): Color {
        return when (status) {
            "Completed" -> Color.Green
            "Pending" -> Color.Yellow
            "Failed" -> Color.Red
            else -> Color.Gray
        }
    }

    data class Transaction(
        val account: String,
        val amount: Double,
        val date: LocalDate,
        val status: String
    )
}
