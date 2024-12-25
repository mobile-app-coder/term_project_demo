package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screens.items.DateInputField
import screens.items.PhoneInputField
import viewmodels.RegisterViewModel

class RegisterScreen : Screen {

    @Composable
    @Preview
    override fun Content() {
        val viewModel: RegisterViewModel = viewModel { RegisterViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        var selectedDate by remember { mutableStateOf("") }

        // Colors
        val backgroundColor = Color(0xFFF4F6F8) // Light grey
        val sidebarColor = Color(0xFF0D47A1) // Dark blue
        val buttonColor = Color(0xFF6200EA) // Purple
        val textFieldColor = Color(0xFFFFFFFF) // White
        val textColor = Color(0xFF212121) // Dark gray

        var passwordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }
        // Validation state
        val isFormValid by derivedStateOf {
            viewModel.firstName.value.isNotBlank() &&
                    selectedDate.isNotBlank() &&
                    viewModel.address.value.isNotBlank() &&
                    viewModel.phone.value.isNotBlank() &&
                    viewModel.email.value.isNotBlank() &&
                    viewModel.login.value.isNotBlank() &&
                    viewModel.passportId.isNotBlank() &&
                    viewModel.password.value.isNotBlank() &&
                    viewModel.password.value == viewModel.passwordConfirmation.value
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            topBar = {
                TopAppBar(

                    backgroundColor = sidebarColor,
                    contentColor = Color.White,
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp) // Set a size for the circular container
                                    .clip(CircleShape)
                            ) {
                                Image(
                                    painter = painterResource("images/logo.jpg"),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.clip(CircleShape).size(60.dp)

                                )
                            }
                            Text("IUT BANK", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        ) {

            // Main content
            if (viewModel.isLoading.value) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = sidebarColor)
                        Text("Wait, your data is processing")
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(32.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            "Create Your Account",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Input fields
                        val textFieldModifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)

                        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = textFieldColor,
                            focusedBorderColor = sidebarColor,
                            unfocusedBorderColor = Color.Gray,
                            textColor = textColor,
                            cursorColor = sidebarColor
                        )

                        OutlinedTextField(
                            value = viewModel.firstName.value,
                            onValueChange = { viewModel.firstName.value = it },
                            label = { Text("First name") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )

                        OutlinedTextField(
                            value = viewModel.lastName.value,
                            onValueChange = { viewModel.firstName.value = it },
                            label = { Text("Last name") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )

                        OutlinedTextField(
                            value = viewModel.passportId,
                            onValueChange = { viewModel.passportId = it },
                            label = { Text("Passport Id") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )

                        DateInputField(
                            onDateChange = {
                                viewModel.dateOfBirth.value = it
                                selectedDate = it
                            }
                        )

                        OutlinedTextField(
                            value = viewModel.address.value,
                            onValueChange = { viewModel.address.value = it },
                            label = { Text("Address") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )

                        PhoneInputField(
                            phoneNumber = viewModel.phone.value,
                            onPhoneChange = {
                                viewModel.phone.value = it
                            }
                        )

                        OutlinedTextField(
                            value = viewModel.email.value,
                            onValueChange = { viewModel.email.value = it },
                            label = { Text("Email") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )

                        OutlinedTextField(
                            value = viewModel.login.value,
                            onValueChange = { viewModel.login.value = it },
                            label = { Text("Login") },
                            colors = textFieldColors,
                            modifier = textFieldModifier
                        )


                        OutlinedTextField(
                            value = viewModel.password.value,
                            onValueChange = { viewModel.password.value = it },
                            label = { Text("Password") },
                            colors = textFieldColors,
                            modifier = textFieldModifier,
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(
                                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                                    )
                                }
                            }
                        )

                        OutlinedTextField(
                            value = viewModel.passwordConfirmation.value,
                            onValueChange = { viewModel.passwordConfirmation.value = it },
                            label = { Text("Confirm Password") },
                            colors = textFieldColors,
                            modifier = textFieldModifier,
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (confirmPasswordVisibility) "Hide Password" else "Show Password"
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                viewModel.register(navigator)
                                //navigator.replace(LoginScreen())
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isFormValid) buttonColor else Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            //enabled = isFormValid
                        ) {
                            Text("Register", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Row {
                            Text("Dou you have an account", modifier = Modifier.clickable {
                                navigator.push(LoginScreen())
                            })
                        }

                        if (viewModel.errorMessage.value.isNotBlank()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = viewModel.errorMessage.value,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

