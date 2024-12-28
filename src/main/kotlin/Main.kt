import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.Network
import screens.BranchScreen
import screens.HomeScreen


fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication, title = "OS BANKING",
            state = WindowState(WindowPlacement.Fullscreen)
        ) {
            Navigator(HomeScreen("11"))
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            Network.sendMessage("Compose")
        }
    }

}
