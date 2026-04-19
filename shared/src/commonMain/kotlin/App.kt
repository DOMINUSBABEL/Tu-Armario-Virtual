import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
val CyanNeon = Color(0xFF00FFFF)
val Gold = Color(0xFFFFD700)

val AppTypography = Typography(
    h3 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 1.25.sp
    )
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    MaterialTheme(
        colors = darkColors(
            primary = NeonPink,
            primaryVariant = HotPink,
            secondary = CyanNeon,
            background = DarkBackground,
            surface = Color(0xFF252542),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        ),
        typography = AppTypography
    ) {
        var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Login) }

        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) + slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { fullHeight -> fullHeight / 4 }
                ) togetherWith fadeOut(animationSpec = tween(300)) + slideOutVertically(
                    animationSpec = tween(300),
                    targetOffsetY = { fullHeight -> -fullHeight / 4 }
                )
            }
        ) { screen ->
            when (screen) {
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
}

expect fun getPlatformName(): String
