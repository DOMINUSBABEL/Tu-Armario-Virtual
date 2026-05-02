package com.myapplication.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using Compose Multiplatform defaults, but applying ExtraBold and heavier weights
// for a "Comfortable Disruption" and "Neon Cyber Glam" aesthetic.

val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold, // Impactful headers
        fontSize = 40.sp,
        letterSpacing = (-1.0).sp,
        color = TextPrimary
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        letterSpacing = (-0.5).sp,
        color = TextPrimary
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        letterSpacing = 0.sp,
        color = TextPrimary
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp,
        color = TextPrimary
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp,
        color = TextPrimary
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = TextSecondary
    ),
    button = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        letterSpacing = 1.25.sp,
        color = Color.Black // Contrast for bright neon buttons
    ),
    caption = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = TextSecondary
    ),
    overline = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
        color = ElectricCyan // Switched to ElectricCyan for cyberpunk accents
    )
)
