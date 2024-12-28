package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class BranchScreen : Screen {



    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var isExtended by mutableStateOf(false)

        val branches = listOf(
            "Tashkent", "Chirchiq", "Angren", "Bekabad",
            "Bukhara", "G‘ijduvon", "Kogon",
            "Fergana", "Marg‘ilon", "Qo‘qon", "Oltiariq",
            "Samarkand", "Urgut", "Kattaqo‘rg‘on", "Bulung‘ur"
        )

        var branch by mutableStateOf("Choose branch")
        Scaffold(Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = { Text("Choose branch") }
            )
        }) {
            Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.5f)) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(onClick = { isExtended = true }, modifier = Modifier.fillMaxWidth()) {
                            Text(branch)
                        }
                        DropdownMenu(
                            expanded = isExtended,
                            onDismissRequest = { isExtended = false },
                            modifier = Modifier.fillMaxWidth(),

                            ) {
                            branches.forEach { item ->
                                DropdownMenuItem(onClick = {
                                    isExtended = false
                                    branch = item
                                }) {
                                    Text(item)
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(onClick = {
                        navigator.push(LoginScreen())
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Continue")
                    }
                }
            }
        }
    }

}