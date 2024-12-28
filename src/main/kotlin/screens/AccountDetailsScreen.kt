package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        var name by remember { mutableStateOf("Turaev Shahriyor") }
        var email by remember { mutableStateOf("shahriyor4@gmail.com") }
        var phone by remember { mutableStateOf("+998 99 368 26 14") }

        var isEditing by remember { mutableStateOf(false) }
        var newName by remember { mutableStateOf(name) }
        var newEmail by remember { mutableStateOf(email) }
        var newPhone by remember { mutableStateOf(phone) }

        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header Section
                Text(
                    text = "Account Details",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Account Information Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isEditing) {
                        TextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        TextField(
                            value = newEmail,
                            onValueChange = { newEmail = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        TextField(
                            value = newPhone,
                            onValueChange = { newPhone = it },
                            label = { Text("Phone") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Name: $name", style = TextStyle(fontSize = 20.sp))
                        Text("Email: $email", style = TextStyle(fontSize = 20.sp))
                        Text("Phone: $phone", style = TextStyle(fontSize = 20.sp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Linked Accounts Section
                Text(
                    text = "Linked Accounts",
                    style = MaterialTheme.typography.h6
                )
                LinkedAccountsList(accounts = listOf("Account 1", "Account 2"))

                Spacer(modifier = Modifier.height(16.dp))

                // Balance Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Balance:", style = TextStyle(fontSize = 20.sp))
                    Text("$19 012", style = TextStyle(fontSize = 20.sp, color = Color.Green))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Button to toggle between view and edit mode
                Button(
                    onClick = {
                        if (isEditing) {
                            // Save changes when editing is complete
                            name = newName
                            email = newEmail
                            phone = newPhone
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditing) "Save Changes" else "Edit Details")
                }
            }
        }
    }
}

// Linked Accounts List
@Composable
fun LinkedAccountsList(accounts: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        accounts.forEach { account ->
            LinkedAccountItem(account)
        }
    }
}

// Linked Account Item
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
