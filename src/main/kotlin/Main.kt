import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import screens.HomeScreen


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = "OS BANKING",
        state = WindowState(WindowPlacement.Fullscreen)
    ) {
       Navigator( HomeScreen())
    }
}
