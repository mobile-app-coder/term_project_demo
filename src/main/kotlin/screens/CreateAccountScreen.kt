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
import viewmodel.CreateAccountViewModel


class CreateBankAccountScreen(private val userId: String) : Screen {
    @Composable
    @Preview
    override fun Content() {
        val viewModel = viewModel { CreateAccountViewModel(userId) }
        val backgroundColor = Color(0xFFF4F6F8) // Light grey
        val buttonColor = Color(0xFF6200EA) // Purple
        val textFieldColor = Color(0xFFFFFFFF) // White
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()

        // State for form inputs


        // Dropdown menu states
        var expandedType by remember { mutableStateOf(false) }
        var expandedCurrency by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
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
                        value = viewModel.accountHolderName,
                        onValueChange = { viewModel.accountHolderName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter Account Holder Name") },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldColor)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Account Type Dropdown
                    Text("Account Type:")
                    Box {
                        Button(
                            onClick = { expandedType = !expandedType },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = viewModel.selectedTypeName
                            )
                        }
                        DropdownMenu(
                            expanded = expandedType,
                            onDismissRequest = { expandedType = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            viewModel.types.forEach { type ->
                                DropdownMenuItem(onClick = {
                                    viewModel.selectType(type)
                                    expandedType = false
                                }) {
                                    Text(type.name)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Currency Dropdown
                    Text("Currency:")
                    Box {
                        Button(
                            onClick = { expandedCurrency = !expandedCurrency },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = viewModel.selectedCurrency.name
                            )
                        }
                        DropdownMenu(
                            expanded = expandedCurrency,
                            onDismissRequest = { expandedCurrency = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            viewModel.currencies.forEach { currency ->
                                DropdownMenuItem(onClick = {
                                    viewModel.selectCurrency(currency)
                                    expandedCurrency = false
                                }) {
                                    Text(currency.name)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Initial Deposit Field
                    Text("Initial Deposit:")
                    OutlinedTextField(
                        value = viewModel.initialDeposit,
                        onValueChange = { viewModel.initialDeposit = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter Initial Deposit") },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldColor)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            viewModel.crateAccount(navigator)
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
