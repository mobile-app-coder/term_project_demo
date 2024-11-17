package screens



import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class HomeScreen : Screen {

    @Composable
    @Preview
    override fun Content() {
        // Custom colors
        val backgroundColor = Color(0xFFECEFF1) // Light grey for the scaffold background
        val buttonColor = Color(0xFF546E7A) // Dark blue-grey for buttons
        val cardBackgroundColor = Color(0xFF607D8B) // Color for the cards

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
                .background(backgroundColor)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Header with name and balance
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Turayev Shahriyor", fontSize = 20.sp)
                        Spacer(Modifier.height(10.dp))
                        Text("$1 000 000", fontSize = 30.sp)
                    }
                    Spacer(Modifier.weight(1f))

                    // Profile or other action icon (e.g., a Circle for profile pic)
                    Box(
                        modifier = Modifier
                            .background(shape = CircleShape, color = buttonColor)
                            .height(50.dp)
                            .width(50.dp)
                    ) {}
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons (Loan, Withdraw, Deposit, Funds, Transactions)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionButton(title = "Loan", backgroundColor = cardBackgroundColor)
                    ActionButton(title = "Withdraw", backgroundColor = cardBackgroundColor)
                    ActionButton(title = "Deposit", backgroundColor = cardBackgroundColor)
                    ActionButton(title = "Funds", backgroundColor = cardBackgroundColor)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Info Cards for quick access to features (Loans, Deposits, Withdrawals, etc.)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCard(title = "Loans", description = "Apply for a loan easily.", backgroundColor = cardBackgroundColor)
                    InfoCard(title = "Deposit", description = "Deposit money into your account.", backgroundColor = cardBackgroundColor)
                    InfoCard(title = "Withdraw", description = "Withdraw funds from your account.", backgroundColor = cardBackgroundColor)
                    InfoCard(title = "Transactions", description = "View your transaction history.", backgroundColor = cardBackgroundColor)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Scrollable section for transactions or other features
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = 10) { index ->
                        CreditCard(
                            cardNumber = "9860 1111 2222 3333",
                            balance = "$1 000",
                            bankName = "BRB",
                            cardName = "Uzcard",
                            backgroundColor = buttonColor
                        )
                    }
                }
            }
        }
    }
}

// ActionButton for performing bank actions
@Composable
fun ActionButton(title: String, backgroundColor: Color) {
    Button(
        onClick = { /* Handle click action, e.g., navigate to the respective screen */ },
        modifier = Modifier
            .height(100.dp)

            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(text = title, color = Color.White, fontSize = 18.sp)
    }
}

// InfoCard to display important bank features
@Composable
fun InfoCard(title: String, description: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = title, color = Color.White, fontSize = 22.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

// Credit Card representation
@Composable
fun CreditCard(
    cardNumber: String,
    balance: String,
    bankName: String,
    cardName: String,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .width(250.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                Row {
                    Text(bankName)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(cardName)
                }
                Spacer(Modifier.height(10.dp))
                Text(cardNumber)
                Spacer(Modifier.height(10.dp))
                Text(balance)
            }
        }
    }
}
