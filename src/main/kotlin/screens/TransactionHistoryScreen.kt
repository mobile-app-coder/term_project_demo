package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import java.time.format.TextStyle

// Transaction History Screen
class TransactionHistoryScreen : Screen {
    @Composable
    override fun Content() {
        @Composable
        fun TransactionHistoryScreen() {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Transaction History", style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp))

                // Filter Dropdown
                var selectedFilter by remember { mutableStateOf("All") }
                DropdownField(
                    label = "Filter By",
                    options = listOf("All", "Deposits", "Withdrawals", "Payments"),
                    selectedOption = selectedFilter,
                    onOptionSelected = { selectedFilter = it }
                )

                Spacer(Modifier.height(16.dp))

                // Transaction List
                //TransactionList(transactions = listOf(/* Dummy transactions */))
            }
        }

    }
}