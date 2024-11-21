package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

// Account Details Screen
class AccountDetailsScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Account Details", style = TextStyle(fontSize = 24.sp))

            Spacer(Modifier.height(16.dp))

            // Account Information
            Text("Name: John Doe", style = TextStyle(fontSize = 18.sp))
            Text("Email: john.doe@example.com", style = TextStyle(fontSize = 18.sp))
            Text("Phone: +123 456 7890", style = TextStyle(fontSize = 18.sp))

            Spacer(Modifier.height(16.dp))

            // Linked Accounts/Cards
            Text("Linked Accounts", style = TextStyle(fontSize = 20.sp))
            LinkedAccountsList(accounts = listOf("Account 1", "Account 2"))
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
            style = TextStyle(fontSize = 16.sp, color = Color.Black)
        )
    }
}
