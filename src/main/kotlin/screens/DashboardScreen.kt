package screens

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import screens.items.*
import server.model.AccountTransfer
import viewmodels.HomeScreenViewModel


// Dashboard Screen
class DashboardScreen(private val userId: String? = null) : Screen {


    val sampleData = listOf(
        AccountTransfer(senderAccountId = 101, receiverAccountId = 202, amount = 150.50),
        AccountTransfer(senderAccountId = 303, receiverAccountId = 404, amount = 250.75),
        AccountTransfer(senderAccountId = 101, receiverAccountId = 303, amount = 100.00),
        AccountTransfer(senderAccountId = 404, receiverAccountId = 101, amount = 300.20),
        AccountTransfer(senderAccountId = 202, receiverAccountId = 404, amount = 500.00),
        AccountTransfer(senderAccountId = 303, receiverAccountId = 101, amount = 75.25),
        AccountTransfer(senderAccountId = 404, receiverAccountId = 202, amount = 50.00),
        AccountTransfer(senderAccountId = 101, receiverAccountId = 404, amount = 250.60),
        AccountTransfer(senderAccountId = 202, receiverAccountId = 303, amount = 125.30),
        AccountTransfer(senderAccountId = 101, receiverAccountId = 202, amount = 200.80)
    )




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
        val view = viewModel { HomeScreenViewModel(userId!!) }

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

        val viewModel = viewModel { HomeScreenViewModel(userId!!) }

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
                    Column(Modifier.weight(1f)) {
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
                        Transaction(viewModel)

                    }

                    Column(Modifier.weight(2f)) {
                        Row {

                            ApplicationList(modifier = Modifier.weight(1f).padding(20.dp))
                            Column(
                                modifier = Modifier.weight(1f).padding(16.dp).verticalScroll(rememberScrollState())
                            ) {
                                Text("Recent transactions", fontSize = 20.sp, color = Color.Gray)
                                for (i in sampleData.indices - 1) {
                                    TransactionItem(viewModel.accountId, sampleData[i])
                                }
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

    @Composable
    fun TransactionItem(accountId: Int, accountTransfer: AccountTransfer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (accountTransfer.senderAccountId == accountId) Color(0xFFFFEBEE) else Color(0xFFE8F5E9))
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = if (accountTransfer.senderAccountId == accountId)
                    Icons.Default.ArrowCircleRight else Icons.Default.ArrowCircleLeft,
                contentDescription = null,
                tint = if (accountTransfer.senderAccountId == accountId) Color.Red else Color.Green,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "From: ${accountTransfer.senderAccountId}",
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
                )
                Text(
                    text = "To: ${accountTransfer.receiverAccountId}",
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
                )
            }
            Text(
                text = "\$${String.format("%.2f", accountTransfer.amount)}",
                style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primary)
            )
        }
    }


    @Composable
    fun Transaction(viewModel: HomeScreenViewModel) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {

            Column {
                Text("Transfer your money", fontSize = 20.sp)

                OutlinedTextField(
                    value = viewModel.transferDestination,
                    onValueChange = { viewModel.transferDestination = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Enter account nmuber") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.transferMoney,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { viewModel.transferMoney = it },
                    label = { Text("Enter amount of money") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.passportId,
                    onValueChange = { viewModel.passportId = it },
                    label = { Text("Enter passport id") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.transferDescription,
                    onValueChange = { viewModel.transferDescription = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = {
                        viewModel.transfer()

                    }, enabled = viewModel.transferDestination.isNotEmpty() &&
                            viewModel.transferMoney.isNotEmpty() &&
                            viewModel.passportId.isNotEmpty() &&
                            viewModel.transferDescription.isNotEmpty()
                ) {
                    Text("Apply")
                }

            }
        }
    }

}
