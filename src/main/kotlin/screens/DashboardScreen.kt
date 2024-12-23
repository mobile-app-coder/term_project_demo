package screens

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import screens.items.*
import viewmodels.HomeScreenViewModel
import java.time.LocalDate


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
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ServiceCard(
                        "Loan Management", "Active Loans: $12,000",

                        Modifier.size(150.dp).padding(10.dp).background(Color(0xFF4CAF50)).clickable(onClick = {
                            navigator?.replace(LoanScreen())
                        }).padding(16.dp).weight(1f)
                    )
                    ServiceCard(
                        "Deposits", "Total: $25,000",

                        Modifier.size(150.dp).padding(10.dp).background(Color(0xFFFFC107)).clickable(onClick = {
                            navigator?.replace(DepositScreen())
                        }).padding(16.dp).weight(1f)
                    )
                    ServiceCard(
                        "Withdrawals", "Pending: $3,500",

                        Modifier.size(150.dp).padding(10.dp).background(Color(0xFF2196F3)).clickable(onClick = {
                            navigator?.replace(WithdrawalScreen())
                        }).padding(16.dp).weight(1f)
                    )
                    ServiceCard(
                        "Transactions", "Recent: $2,500",

                        Modifier.size(150.dp).padding(10.dp).background(Color(0xFFFF5722)).clickable(onClick = {
                            navigator?.replace(TransactionHistoryScreen())
                        }).padding(16.dp).weight(1f)
                    )
                }
                Row {
                    Column(Modifier.fillMaxWidth(0.7f)) {
                        Spacer(Modifier.height(16.dp))

                        // Credit Cards: Evenly distributed
                        Text(
                            "Credit Cards", fontSize = 20.sp, color = Color.Gray
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp).horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (i in 1..10) {
                                CreditCard("Visa", "9869 2221 3456 7897", "$5,000", Color(0xFF1E88E5))
                            }
                        }
                        // Transaction History
                        Text("Recent Transactions", fontSize = 20.sp, color = Color.Gray)

                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            //show messages
                            viewModel.items.forEach { s: String ->
                                TransactionItem(s, s, "today", Color.Green)
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
                                    "Super Car:Ferrari F8", "Car loan", "Approved", LocalDate.now(), "$1 000 000"
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
                SettingsPanel(onClose = { viewModel.showSettings.value = false })
            }
            // Settings Panel
            AnimatedVisibility(
                visible = viewModel.showSettings.value,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {

                NotificationPanel(onClose = { viewModel.showNotifications.value = false })
            }
        }
    }

}
