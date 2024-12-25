package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

// Account Details Screen
class AccountDetailsScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold {
            Row(modifier = Modifier.padding(16.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)

                ) {
                    Text(
                        text = "Account Details",
                        style = MaterialTheme.typography.h5
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Account Information
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Name: John Doe", style = MaterialTheme.typography.body1,fontSize = 25.sp)
                        Text("Email: john.doe@example.com", style = MaterialTheme.typography.body1,fontSize = 25.sp)
                        Text("Phone: +123 456 7890", style = MaterialTheme.typography.body1,fontSize = 25.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Linked Accounts/Cards
                    Text(
                        text = "Linked Accounts",
                        style = MaterialTheme.typography.h6
                    )

                    LinkedAccountsList(accounts = listOf("Account"))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Balance Section
                }
                Row(
                    modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Balance:", fontSize = 25.sp)
                    Text("$1,000,000,000", fontSize = 25.sp)
                }
                Box(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun LinkedAccountsList(accounts: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        accounts.forEach { account ->
            LinkedAccountItem(account)
        }
    }
}

@Composable
fun LinkedAccountItem(account: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = account,
            style = MaterialTheme.typography.body2.copy(color = Color.Black)
        )
    }
}
