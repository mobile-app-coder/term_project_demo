package screens



import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CreateAccountScreen : Screen {
    @Composable
    @Preview
    override fun Content() {
        val backgroundColor = Color(0xFFECEFF1) // Light grey for the scaffold background
        val textFieldColor = Color(0xFFCFD8DC) // Light blue-grey for text fields
        val buttonColor = Color(0xFF546E7A) //

        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .background(color = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F) // Darker text color
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navigator.replaceAll(HomeScreen())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = buttonColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Existing account")
                }
            }
        }
    }

}