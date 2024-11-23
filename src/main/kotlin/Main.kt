import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import network.Network
import screens.HomeScreen


fun main() {
    Network.sendMessage("Compose")
    application {
        Window(
            onCloseRequest = ::exitApplication, title = "OS BANKING",
        ) {
            Navigator(HomeScreen())
        }
    }

}
