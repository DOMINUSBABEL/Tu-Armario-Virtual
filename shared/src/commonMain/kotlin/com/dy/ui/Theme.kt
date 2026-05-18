package com.dy.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Isa "Soft-Tech" Color Palette
val md_theme_light_primary = Color(0xFF6750a4)
val md_theme_light_onPrimary = Color(0xFFffffff)
val md_theme_light_primaryContainer = Color(0xFFeaddff)
val md_theme_light_onPrimaryContainer = Color(0xFF21005d)

val md_theme_light_secondary = Color(0xFFd6c6e1)
val md_theme_light_onSecondary = Color(0xFF3b2d47)
val md_theme_light_secondaryContainer = Color(0xFFf3edf7)
val md_theme_light_onSecondaryContainer = Color(0xFF1e192b)

val md_theme_light_tertiary = Color(0xFFb4eeb4)
val md_theme_light_onTertiary = Color(0xFF00390a)
val md_theme_light_tertiaryContainer = Color(0xFFd0f8d0)
val md_theme_light_onTertiaryContainer = Color(0xFF002104)

val md_theme_light_error = Color(0xFFba1a1a)
val md_theme_light_onError = Color(0xFFffffff)
val md_theme_light_errorContainer = Color(0xFFffdad6)
val md_theme_light_onErrorContainer = Color(0xFF410002)

val md_theme_light_surface = Color(0xFFf8f9fa)
val md_theme_light_onSurface = Color(0xFF1b1b1f)
val md_theme_light_surfaceVariant = Color(0xFFd9dadb)
val md_theme_light_onSurfaceVariant = Color(0xFF44474e)

// Typography Configuration based on Plus Jakarta Sans
val IsaTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default, // Replace with Plus Jakarta Sans when imported
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
        lineHeight = 67.2.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 41.6.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 14.4.sp
    )
)

// Isa Spacing & Roundness (Soft-Tech)
object IsaDimens {
    val BaseSpacing = 4.dp
    val Gutter = 24.dp
    val MarginDesktop = 40.dp
    val MarginMobile = 16.dp
    
    val RoundNone = 0.dp
    val RoundSmall = 4.dp
    val RoundMedium = 8.dp
    val RoundLarge = 16.dp
    val RoundFull = 9999.dp
}
