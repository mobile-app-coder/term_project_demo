package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

// Withdrawal Screen
class WithdrawalScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Withdraw Money", style = TextStyle(fontSize = 24.sp))

            // Withdrawal Amount Input
            var withdrawalAmount by remember { mutableStateOf("") }
            TextField(
                label = { Text("Withdrawal Amount") },
                value = withdrawalAmount,
                onValueChange = { withdrawalAmount = it })

            // Account Selection Dropdown
            var selectedAccount by remember { mutableStateOf("Account 1") }
            DropdownField(
                label = "Select Account",
                options = listOf("Account 1", "Account 2"),
                selectedOption = selectedAccount,
                onOptionSelected = { selectedAccount = it }
            )

            // Withdraw Button
            Button(onClick = { /* Process withdrawal */ }) {
                Text("Submit Withdrawal")
            }

            Spacer(Modifier.height(16.dp))

            // Recent Withdrawals
            Text("Recent Withdrawals", style = TextStyle(fontSize = 20.sp))
            //TransactionList(transactions = listOf(/* Dummy transactions */))
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Tracks if dropdown is open

    Column {
        // Label for the dropdown
        Text(text = label, style = TextStyle(fontSize = 16.sp, color = Color.Gray))

        // Dropdown menu button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp)
                .clickable { expanded = true }
        ) {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                style = TextStyle(fontSize = 14.sp, color = Color.Black)
            )
        }

        // Dropdown menu items
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option) // Handle selection
                    expanded = false // Close dropdown
                }) {
                    Text(text = option)
                }
            }
        }
    }
}
