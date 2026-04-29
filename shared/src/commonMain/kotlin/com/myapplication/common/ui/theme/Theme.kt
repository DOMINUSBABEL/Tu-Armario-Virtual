package com.myapplication.common.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

private val LightColorPalette = lightColors(
    primary = PrimaryDarkPurple,
    primaryVariant = PrimaryDarkPurple,
    secondary = AccentMagenta,
    secondaryVariant = AccentMagenta,
    background = BackgroundWhite,
    surface = SurfaceLight,
    error = Color(0xFFB00020),
    onPrimary = White,
    onSecondary = White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = White
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
