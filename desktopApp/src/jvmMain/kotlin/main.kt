import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    com.myapplication.common.db.DatabaseRepository.init(
        com.myapplication.common.DatabaseDriverFactory()
    )
    Window(onCloseRequest = ::exitApplication) {
        MainView()
    }
}