package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class LoanScreen : Screen {

    @Composable
    @Preview
    override fun Content() {
        MaterialTheme {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF6F6F6))
                    .padding(16.dp)
            ) {
                // Top Bar
                TopBar()

                Spacer(Modifier.height(16.dp))

                // Loan Types List
                LoanList()
            }
        }
    }

    @Composable
    fun TopBar() {
        Text(
            text = "Available Loan Options",
            style = TextStyle(fontSize = 24.sp, color = Color.Black),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

    @Composable
    fun LoanList() {
        val navigator = LocalNavigator.current
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LoanCard(
                title = "Personal Loan",
                description = "A loan for personal needs like home renovation, medical expenses, or travel.",
                benefits = listOf("Flexible repayment options", "Quick disbursal", "No collateral required"),
                onApply = { navigator?.push(ApplyLoanScreen("Personal Loan")) }
            )
            LoanCard(
                title = "Home Loan",
                description = "A loan to purchase or construct your dream home.",
                benefits = listOf("Low interest rates", "Longer tenure options", "Tax benefits"),
                onApply = { navigator?.push(ApplyLoanScreen("Home")) }
            )
            LoanCard(
                title = "Car Loan",
                description = "Finance your new or used car purchase with ease.",
                benefits = listOf("Affordable EMIs", "Quick processing", "Up to 90% financing"),
                onApply = { navigator?.push(ApplyLoanScreen("Car")) }
            )
            LoanCard(
                title = "Education Loan",
                description = "Support your or your child's education with this loan.",
                benefits = listOf(
                    "Covers tuition and living expenses",
                    "Flexible repayment after graduation",
                    "Lower interest rates for higher studies"
                ),
                onApply = { navigator?.push(ApplyLoanScreen("Education Loan")) }
            )
            LoanCard(
                title = "Business Loan",
                description = "Expand or establish your business with adequate funding.",
                benefits = listOf("Customizable loan options", "No collateral for smaller amounts", "Quick approval"),
                onApply = { navigator?.push(ApplyLoanScreen("Business Loan")) }
            )
        }
    }

    @Composable
    fun LoanCard(
        title: String,
        description: String,
        benefits: List<String>,
        onApply: () -> Unit
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 20.sp, color = Color.Black)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Benefits:",
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
            Column(
                Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                benefits.forEach { benefit ->
                    Text(
                        text = "- $benefit",
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onApply,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Apply Now", color = Color.White)
            }
        }
    }
}
