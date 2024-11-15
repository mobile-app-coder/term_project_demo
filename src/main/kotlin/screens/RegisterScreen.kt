package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import view_models.RegisterViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun RegisterScreen() {
    val viewModel: RegisterViewModel = viewModel { RegisterViewModel() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Custom colors
    val backgroundColor = Color(0xFFECEFF1) // Light grey for the scaffold background
    val textFieldColor = Color(0xFFCFD8DC) // Light blue-grey for text fields
    val buttonColor = Color(0xFF546E7A) // Darker blue-grey for the button

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Register",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF37474F) // Darker text color
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
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
                    value = viewModel.name.value,
                    onValueChange = { viewModel.name.value = it },
                    label = { Text("Name") },
                    colors = textFieldColors
                )

                OutlinedTextField(
                    modifier = textFieldModifier,
                    value = viewModel.dateOfBirth.value,
                    onValueChange = { viewModel.dateOfBirth.value = it },
                    label = { Text("Date of Birth") },
                    colors = textFieldColors
                )

                OutlinedTextField(
                    modifier = textFieldModifier,
                    value = viewModel.address.value,
                    onValueChange = { viewModel.address.value = it },
                    label = { Text("Address") },
                    colors = textFieldColors
                )

                OutlinedTextField(
                    modifier = textFieldModifier,
                    value = viewModel.phone.value,
                    onValueChange = { viewModel.phone.value = it },
                    label = { Text("Phone") },
                    colors = textFieldColors
                )

                OutlinedTextField(
                    modifier = textFieldModifier,
                    value = viewModel.email.value,
                    onValueChange = { viewModel.email.value = it },
                    label = { Text("Email") },
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.height(16.dp))

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

                OutlinedTextField(
                    modifier = textFieldModifier,
                    value = viewModel.passwordConfirmation.value,
                    onValueChange = { viewModel.passwordConfirmation.value = it },
                    label = { Text("Confirm Password") },
                    colors = textFieldColors
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { /* Handle registration logic */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonColor,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Register", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
