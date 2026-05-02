package com.myapplication.common.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp), // Slightly more rounded for organic feel
    large = RoundedCornerShape(28.dp)
)

private val DarkColorPalette = darkColors(
    primary = NeonPeach,
    primaryVariant = DeepPurple, // Synthwave deep purple for variants
    secondary = ElectricCyan,    // Electric cyan for secondary interactive elements
    secondaryVariant = HolographicSilver,
    background = OnyxBlack,
    surface = SurfaceDark,
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = Color.Black
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
