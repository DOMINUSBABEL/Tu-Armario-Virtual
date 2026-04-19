import androidx.compose.ui.window.ComposeUIViewController

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { 
    com.myapplication.common.db.DatabaseRepository.init(
        com.myapplication.common.DatabaseDriverFactory()
    )
    App() 
}