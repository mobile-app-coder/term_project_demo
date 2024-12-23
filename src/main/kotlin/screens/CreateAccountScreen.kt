package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import viewmodels.CreateAccountViewModel

class CreateBankAccountScreen(private val userId: String) : Screen {
    @Composable
    @Preview
    override fun Content() {

        val viewModel = viewModel {
            CreateAccountViewModel(userId)
        }

        val backgroundColor = Color(0xFFF4F6F8) // Light grey
        val buttonColor = Color(0xFF6200EA) // Purple
        val textFieldColor = Color(0xFFFFFFFF) // White
        val navigator = LocalNavigator.currentOrThrow

        // State for form inputs
        var accountHolderName by remember { mutableStateOf("") }
        var accountType by remember { mutableStateOf("Select Account Type") }
        var initialDeposit by remember { mutableStateOf("") }

        // Dropdown menu states
        var expanded by remember { mutableStateOf(false) }
        val accountTypes = listOf("Checking", "Savings", "Business", "Fixed Deposit")

        Scaffold(
            modifier = Modifier.fillMaxSize()
                .background(color = backgroundColor),

            ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Create Bank Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F) // Darker text color
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Account Holder Name Field
                    Text("Account Holder Name:")
                    OutlinedTextField(
                        value = accountHolderName,
                        onValueChange = { accountHolderName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter Account Holder Name") },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Account Type Dropdown
                    Text("Account Type:")

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { expanded = !expanded }, content = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(accountType)
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.fillMaxWidth(0.6f)
                                ) {
                                    viewModel.types.forEach { type ->
                                        DropdownMenuItem(onClick = {
                                            accountType = type.name
                                            expanded = false
                                        }) {
                                            Text(type.name)
                                        }
                                    }
                                }
                            }
                        })



                    Spacer(modifier = Modifier.height(10.dp))

                    // Initial Deposit Field
                    Text("Initial Deposit:")
                    OutlinedTextField(
                        value = initialDeposit,
                        onValueChange = { initialDeposit = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter Initial Deposit") },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldColor)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            // Handle the account creation logic here
                            // You can pass the user inputs (accountHolderName, accountType, initialDeposit)
                            // to a function that will create the account in the backend or database.

                            // Navigating to the HomeScreen (example)
                            navigator.replaceAll(HomeScreen(""))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = buttonColor,
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Create Account")
                    }
                }
            }
        }
    }
}
