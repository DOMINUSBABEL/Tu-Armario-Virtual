import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dy.ui.DressYourselfTheme
import com.dy.ui.DiscoverScreen
import com.dy.ui.WardrobeScreen
import com.dy.ui.IsaAssistantScreen

// Simulating navigation for the Soft-Tech Redesign
sealed class SoftTechScreen(val route: String) {
    object Discover : SoftTechScreen("Discover")
    object Wardrobe : SoftTechScreen("Wardrobe")
    object IsaAssistant : SoftTechScreen("IsaAssistant")
}

@Composable
fun App() {
    DressYourselfTheme {
        var currentRoute by remember { mutableStateOf<String>(SoftTechScreen.Discover.route) }

        val onNavigate: (String) -> Unit = { route ->
            currentRoute = route
        }

        when (currentRoute) {
            SoftTechScreen.Discover.route -> DiscoverScreen(currentRoute, onNavigate)
            SoftTechScreen.Wardrobe.route -> WardrobeScreen(currentRoute, onNavigate)
            SoftTechScreen.IsaAssistant.route -> IsaAssistantScreen(currentRoute, onNavigate)
        }
    }
}

expect fun getPlatformName(): String
