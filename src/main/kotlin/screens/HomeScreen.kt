package screens

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import viewmodels.HomeScreenViewModel
import java.time.LocalDate

// Main HomeScreen with a permanent sidebar
class HomeScreen : Screen {
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
        // Observe the current screen from the navigator
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
                .background(if (isSelected) Color(0xFF003399) else Color.Transparent)
                .clickable { onClick() }
                .padding(all = 4.dp) // Optional background highlight
        )
    }


}

// Dashboard Screen
class DashboardScreen() : Screen {
    @Composable
    @Preview
    override fun Content() {
        MaterialTheme {
            Column(Modifier.fillMaxSize().background(Color(0xFFF6F6F6)).padding(16.dp)) {
                TopBar()
                Spacer(Modifier.height(16.dp))
                MainContent()
            }
        }
    }

    @Composable
    fun TopBar() {
        val view = viewModel { HomeScreenViewModel() }
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = view.searchText.value,
                onValueChange = {
                    view.searchText.value = it
                },
                textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
                modifier = Modifier.weight(1f).background(Color.White).padding(8.dp)
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        view.showSettings.value = !view.showSettings.value
                    })
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.Gray,
                modifier = Modifier.clickable(
                    onClick = {
                        view.showNotifications.value = !view.showNotifications.value
                    })
            )
            // Notification Panel

        }
    }

    @Composable
    fun MainContent() {
        val navigator = LocalNavigator.current

        val viewModel = viewModel { HomeScreenViewModel() }

        Box(contentAlignment = Alignment.CenterEnd) {
            Column {
                Text("Banking Services Overview", fontSize = 20.sp, color = Color.Gray)

                Spacer(Modifier.height(16.dp))

                // Total Balance and Currency Prices
                TotalBalanceAndCurrency(viewModel.balance.value)

                Spacer(Modifier.height(16.dp))

                // Service Cards: Evenly distributed
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ServiceCard(
                        "Loan Management",
                        "Active Loans: $12,000",

                        Modifier
                            .size(150.dp)
                            .padding(10.dp)
                            .background(Color(0xFF4CAF50))
                            .clickable(onClick = {
                                navigator?.replace(LoanScreen())
                            })
                            .padding(16.dp).weight(1f)
                    )
                    ServiceCard(
                        "Deposits",
                        "Total: $25,000",

                        Modifier
                            .size(150.dp)
                            .padding(10.dp)
                            .background(Color(0xFFFFC107))
                            .clickable(onClick = {
                                navigator?.replace(DepositScreen())
                            })
                            .padding(16.dp)
                            .weight(1f)
                    )
                    ServiceCard(
                        "Withdrawals",
                        "Pending: $3,500",

                        Modifier
                            .size(150.dp)
                            .padding(10.dp)
                            .background(Color(0xFF2196F3))
                            .clickable(onClick = {
                                navigator?.replace(WithdrawalScreen())
                            })
                            .padding(16.dp).weight(1f)
                    )
                    ServiceCard(
                        "Transactions",
                        "Recent: $2,500",

                        Modifier.size(150.dp)
                            .padding(10.dp)
                            .background(Color(0xFFFF5722))
                            .clickable(onClick = {
                                navigator?.replace(TransactionHistoryScreen())
                            })
                            .padding(16.dp).weight(1f)
                    )
                }
                Row {
                    Column(Modifier.fillMaxWidth(0.7f)) {
                        Spacer(Modifier.height(16.dp))

                        // Credit Cards: Evenly distributed
                        Text(
                            "Credit Cards",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (i in 1..10) {
                                CreditCard("Visa", "9869 2221 3456 7897", "$5,000", Color(0xFF1E88E5))
                            }
                        }
                        // Transaction History
                        Text("Recent Transactions", fontSize = 20.sp, color = Color.Gray)

                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            for (i in 1..10) {
                                TransactionList()
                            }

                        }
                    }

                    Column(Modifier.fillMaxWidth()) {
                        Spacer(Modifier.height(16.dp))
                        Text("Applications", fontSize = 20.sp, color = Color.Gray)
                        Spacer(Modifier.height(16.dp))
                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            for (i in 1..10) {
                                ApplicationItem(
                                    "Super Car:Ferrari F8",
                                    "Car loan",
                                    "Approved",
                                    LocalDate.now(),
                                    "$1 000 000"
                                )
                            }
                        }
                    }
                }

            }

            AnimatedVisibility(
                visible = viewModel.showNotifications.value,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {
                NotificationPanel(onClose = { viewModel.showNotifications.value = false })
            }

            // Settings Panel
            AnimatedVisibility(
                visible = viewModel.showSettings.value,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {
                SettingsPanel(onClose = { viewModel.showSettings.value = false })
            }
        }
    }

}

@Composable
fun NotificationPanel(onClose: () -> Unit) {
    Box(
        Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFF1E88E5), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            .padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Notifications", color = Color.White, style = MaterialTheme.typography.h6)
            Button(onClick = onClose, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Close", color = Color(0xFF1E88E5))
            }
        }
    }
}

@Composable
fun SettingsPanel(onClose: () -> Unit) {
    Box(
        Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFF4CAF50), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            .padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", color = Color.White, style = MaterialTheme.typography.h6)
            Row {

            }
            Button(onClick = onClose, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text("Close", color = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun TotalBalanceAndCurrency(balance: String) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Total Balance", fontSize = 18.sp, color = Color.Gray)
            Text("$ $balance", fontSize = 24.sp, color = Color.Black)
        }
        Column {
            Text("Currency Prices (USD)", fontSize = 18.sp, color = Color.Gray)
            Text("1 USD = 1.2 EUR", fontSize = 14.sp, color = Color.Black)
            Text("1 USD = 110 JPY", fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun ServiceCard(title: String, details: String, modifier: Modifier) {
    Box(
        modifier
    ) {
        Column {
            Text(text = title, color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text(text = details, color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun CreditCard(name: String, cardNumber: String, balance: String, color: Color) {
    Box(
        Modifier.size(250.dp).aspectRatio(3 / 1.5f)
            .background(color, shape = RoundedCornerShape(16.dp)).padding(16.dp)
    ) {
        Column {
            Text(name, color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Text(cardNumber, color = Color.White, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            Text("Balance: $balance", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun TransactionList() {
    Column() {
        TransactionItem("Withdrawal", "-$500", "2024-11-15", Color.Red)
        TransactionItem("Deposit", "+$2,000", "2024-11-12", Color.Green)
        TransactionItem("Loan Payment", "-$1,000", "2024-11-10", Color.Red)
    }
}

@Composable
fun TransactionItem(description: String, amount: String, date: String, amountColor: Color) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(description, fontSize = 16.sp, color = Color.Black)
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
        Text(amount, fontSize = 16.sp, color = amountColor)
    }
}

@Composable
fun ApplicationItem(name: String, applicationType: String, status: String, date: LocalDate, moneyAmount: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
    ) {
        Column {
            Text(name)
            Text(moneyAmount)
            Text(applicationType)
        }
        Text(date.toString())
        Text(
            status,
            color = Color.White,
            modifier = Modifier.size(width = 100.dp, height = 30.dp).background(
                color = when (status) {
                    "Approved" -> Color(0xff85c1e9)
                    "Waiting" -> Color.Green
                    else -> Color.Red
                }
            ).padding(all = 4.dp).align(alignment = Alignment.CenterVertically)
        )
    }
}
