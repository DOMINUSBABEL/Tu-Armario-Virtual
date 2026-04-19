import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.myapplication.common.navigation.AppScreen
import com.myapplication.common.ui.LoginScreen
import com.myapplication.common.ui.TutorialScreen
import com.myapplication.common.ui.MainScreen
import com.myapplication.common.ui.LeaderboardScreen
import com.myapplication.common.ui.WardrobeScreen
import com.myapplication.common.ui.RunwayScreen
import com.myapplication.common.ui.RunwayShowcaseScreen

val HotPink = Color(0xFFFF69B4)
val DeepPurple = Color(0xFF4B0082)
val NeonPink = Color(0xFFFF1493)
val DarkBackground = Color(0xFF1A1A2E)

@Composable
fun App() {
    MaterialTheme(
        colors = darkColors(
            primary = HotPink,
            primaryVariant = NeonPink,
            secondary = DeepPurple,
            background = DarkBackground,
            surface = Color(0xFF252542),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }

        when (val screen = currentScreen) {
            is AppScreen.Login -> LoginScreen(onNavigateToTutorial = { currentScreen = AppScreen.Tutorial })
            is AppScreen.Tutorial -> TutorialScreen(onNavigateToMain = { currentScreen = AppScreen.Main })
            is AppScreen.Main -> MainScreen(
                onNavigateToLeaderboard = { currentScreen = AppScreen.Leaderboard },
                onNavigateToWardrobe = { currentScreen = AppScreen.Wardrobe },
                onNavigateToRunway = { currentScreen = AppScreen.Runway }
            )
            is AppScreen.Leaderboard -> LeaderboardScreen(onNavigateBack = { currentScreen = AppScreen.Main })
            is AppScreen.Wardrobe -> WardrobeScreen(onNavigateBack = { currentScreen = AppScreen.Main })
            is AppScreen.Runway -> RunwayScreen(
                onNavigateBack = { currentScreen = AppScreen.Main },
                onNavigateToShowcase = { desc, theme -> currentScreen = AppScreen.RunwayShowcase(desc, theme) }
            )
            is AppScreen.RunwayShowcase -> RunwayShowcaseScreen(
                outfitDescription = screen.outfitDescription,
                theme = screen.theme,
                onNavigateBack = { currentScreen = AppScreen.Main }
            )
        }
    }
}

expect fun getPlatformName(): String
