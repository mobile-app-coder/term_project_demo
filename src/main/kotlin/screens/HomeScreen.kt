package screens


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import viewmodels.HomeScreenViewModel


// Main HomeScreen with a permanent sidebar
class HomeScreen() : Screen {
    @Composable
    @Preview
    override fun Content() {
        MaterialTheme {
            // Create a Navigator
            Navigator(DashboardScreen()) { navigator ->
                Row(Modifier.fillMaxSize()) {
                    // Sidebar is static and always visible
                    Sidebar(navigator)

                    // Main content area changes based on the Navigator's current screen
                    Box(
                        Modifier.weight(1f).fillMaxHeight().background(Color(0xFFF6F6F6))
                    ) {
                        navigator.lastItem.Content()
                    }
                }
            }
        }
    }

    // Sidebar Composable
    @Composable
    fun Sidebar(navigator: Navigator) {
        val viewModel = viewModel { HomeScreenViewModel() }
        val currentScreen = navigator.lastItem


        val selectedItem = remember(currentScreen) {
            when (currentScreen) {
                is DashboardScreen -> "Dashboard"
                is LoanScreen -> "Loans"
                is DepositScreen -> "Deposits"
                is WithdrawalScreen -> "Withdrawals"
                is TransactionHistoryScreen -> "Transaction History"
                is AccountDetailsScreen -> "Account Details"
                else -> "Dashboard"
            }
        }



        Column(
            Modifier.width(200.dp).fillMaxHeight().background(Color(0xFF002366)).padding(16.dp)
        ) {
            Text(
                "BANK LOGO", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.height(16.dp))

            // Sidebar items
            SidebarItem(
                label = "Dashboard",
                isSelected = selectedItem == "Dashboard",
                onClick = { navigator.replace(DashboardScreen()) })
            SidebarItem(
                label = "Loans", isSelected = selectedItem == "Loans", onClick = { navigator.replace(LoanScreen()) })
            SidebarItem(
                label = "Deposits",
                isSelected = selectedItem == "Deposits",
                onClick = { navigator.replace(DepositScreen()) })
            SidebarItem(
                label = "Withdrawals",
                isSelected = selectedItem == "Withdrawals",
                onClick = { navigator.replace(WithdrawalScreen()) })
            SidebarItem(
                label = "Transaction History",
                isSelected = selectedItem == "Transaction History",
                onClick = { navigator.replace(TransactionHistoryScreen()) })
            SidebarItem(
                label = "Account Details",
                isSelected = selectedItem == "Account Details",
                onClick = { navigator.replace(AccountDetailsScreen()) })
        }
    }

    @Composable
    fun SidebarItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
        Text(
            text = label,
            color = if (isSelected) Color.Yellow else Color.White, // Highlight the selected item
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                .background(if (isSelected) Color(0xFF003399) else Color.Transparent).clickable { onClick() }
                .padding(all = 4.dp) // Optional background highlight
        )
    }


}
