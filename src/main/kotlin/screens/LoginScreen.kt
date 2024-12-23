package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import viewmodels.LoginViewModel

 class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = viewModel { LoginViewModel() }
        val navigator = LocalNavigator.currentOrThrow

        // Colors and design constants
        val backgroundColor = Color(0xFFF4F6F8) // Light grey background
        val textFieldBackground = Color.White // White text fields
        val buttonColor = Color(0xFF546E7A) // Blue-grey for buttons
        val textColor = Color(0xFF37474F) // Darker text for labels

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            topBar = {
                TopAppBar(
                    title = { Text("Register", color = textColor) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = textColor
                            )
                        }
                    },
                    backgroundColor = backgroundColor,
                    elevation = 0.dp
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Login to continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Input Form
                Column(
                    modifier = Modifier
                        .widthIn(min = 300.dp, max = 500.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Text Field Styling
                    val textFieldModifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(textFieldBackground, shape = MaterialTheme.shapes.medium)

                    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = buttonColor,
                        unfocusedBorderColor = Color.Gray,
                        textColor = textColor,
                        cursorColor = buttonColor
                    )

                    OutlinedTextField(
                        modifier = textFieldModifier,
                        value = viewModel.login.value,
                        onValueChange = { viewModel.login.value = it },
                        label = { Text("Login", color = textColor) },
                        colors = textFieldColors
                    )

                    OutlinedTextField(
                        modifier = textFieldModifier,
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        label = { Text("Password", color = textColor) },
                        colors = textFieldColors,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            navigator.replaceAll(CreateAccountScreen())
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = buttonColor,
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { /* Navigate to "Forgot Password" */ }) {
                        Text("Forgot Password?", color = buttonColor)
                    }
                }
            }
        }
    }
}
