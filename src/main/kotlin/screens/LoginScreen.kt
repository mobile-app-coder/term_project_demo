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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import models.User
import viewmodels.LoginViewModel


data class LoginScreen(val user: User) : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = viewModel { LoginViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val backgroundColor = Color(0xFFECEFF1) // Light grey for the scaffold background
        val textFieldColor = Color(0xFFCFD8DC) // Light blue-grey for text fields
        val buttonColor = Color(0xFF546E7A) //
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            topBar = {
                TopAppBar(
                    title = { Text("Register") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    },
                    backgroundColor = backgroundColor,
                    elevation = 4.dp
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
                    "Login",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F) // Darker text color
                )
                Text(
                    user.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F) // Darker text color
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .width(600.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Common modifier for text fields
                    val textFieldModifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(textFieldColor, shape = MaterialTheme.shapes.medium)

                    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = buttonColor,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black,
                        cursorColor = buttonColor
                    )



                    OutlinedTextField(
                        modifier = textFieldModifier,
                        value = viewModel.login.value,
                        onValueChange = { viewModel.login.value = it },
                        label = { Text("Login") },
                        colors = textFieldColors
                    )

                    OutlinedTextField(
                        modifier = textFieldModifier,
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        label = { Text("Password") },
                        colors = textFieldColors
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
                        Text("Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

}
