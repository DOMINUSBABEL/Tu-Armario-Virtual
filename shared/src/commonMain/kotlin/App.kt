import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.myapplication.common.navigation.AppScreen
import com.myapplication.common.ui.LoginScreen
import com.myapplication.common.ui.TutorialScreen
import com.myapplication.common.ui.MainScreen

@Composable
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }

        when (currentScreen) {
            is AppScreen.Login -> LoginScreen(onNavigateToTutorial = { currentScreen = AppScreen.Tutorial })
            is AppScreen.Tutorial -> TutorialScreen(onNavigateToMain = { currentScreen = AppScreen.Main })
            is AppScreen.Main -> MainScreen()
        }
    }
}

expect fun getPlatformName(): String