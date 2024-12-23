import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.Network
import screens.RegisterScreen


fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication, title = "OS BANKING",
        ) {
            Navigator(RegisterScreen())
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            Network.sendMessage("Compose")
        }
    }

}
