package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class ApplyLoanScreen(private val loanType: String) : Screen {

    @Composable
    @Preview
    override fun Content() {
        MaterialTheme {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF6F6F6))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Back Button to go back to previous screen
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    BackButton()
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Apply for $loanType Loan",
                    style = TextStyle(fontSize = 24.sp, color = Color.Black),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box(Modifier.fillMaxWidth(0.4f)) {
                    LoanApplicationForm(loanType)
                }
            }
        }
    }

    @Composable
    fun BackButton() {
        val navigator = LocalNavigator.current
        IconButton(
            onClick = { navigator?.pop() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }

    @Composable
    fun LoanApplicationForm(loanType: String) {
        var name by remember { mutableStateOf("") }
        var income by remember { mutableStateOf("") }
        var salary by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var loanPurpose by remember { mutableStateOf("") }
        var carType by remember { mutableStateOf("") }
        var homeLocation by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }

        val navigator = LocalNavigator.current

        Column(verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            InputField(label = "Full Name", value = name, onValueChange = { name = it })
            InputField(label = "Monthly Income", value = income, onValueChange = { income = it })
            InputField(label = "Annual Salary", value = salary, onValueChange = { salary = it })
            InputField(label = "Loan Amount", value = amount, onValueChange = { amount = it })
            InputField(label = "Purpose of Loan", value = loanPurpose, onValueChange = { loanPurpose = it })

            // Conditional fields based on loan type
            when (loanType) {
                "Car" -> {
                    InputField(label = "Car Type (e.g., Sedan, SUV)", value = carType, onValueChange = { carType = it })
                }
                "Home" -> {
                    InputField(label = "Location of Home", value = homeLocation, onValueChange = { homeLocation = it })
                }
            }

            Spacer(Modifier.height(16.dp))

            // Apply Button
            Button(
                onClick = {
                    showDialog = true // Show dialog when application is submitted
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Submit Application", color = Color.White)
            }

            if (showDialog) {
                LoanApplicationDialog(
                    onDismiss = {
                        showDialog = false
                        navigator?.popUntilRoot() // After dismissing, navigate back to the home screen
                    }
                )
            }
        }
    }

    @Composable
    fun LoanApplicationDialog(onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Application Submitted", style = TextStyle(fontSize = 18.sp))
            },
            text = {
                Text("Your loan application has been received. It will be reviewed by the loan manager shortly.")
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
                ) {
                    Text("OK", color = Color.White)
                }
            }
        )
    }

    @Composable
    fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
        Column {
            Text(
                text = label,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
    }
}
