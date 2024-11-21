package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import java.time.format.TextStyle

// Deposit Screen
class DepositScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Deposit Money", style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp))

            // Deposit Amount Input
            var depositAmount by remember { mutableStateOf("") }
            TextField(label = { Text("Deposit Amount") }, value = depositAmount, onValueChange = { depositAmount = it })

            // Account Selection Dropdown
            var selectedAccount by remember { mutableStateOf("Account 1") }
            DropdownField(
                label = "Select Account",
                options = listOf("Account 1", "Account 2"),
                selectedOption = selectedAccount,
                onOptionSelected = { selectedAccount = it }
            )

            // Deposit Button
            Button(onClick = { /* Process deposit */ }) {
                Text("Submit Deposit")
            }

            Spacer(Modifier.height(16.dp))

            // Recent Deposits
            Text("Recent Deposits", style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))
            //TransactionList(transactions = listOf(/* Dummy transactions */))
        }
    }
}