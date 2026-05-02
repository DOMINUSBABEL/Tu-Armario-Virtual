package com.myapplication.common.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState

@Composable
fun Avatar2DView(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val floatAnim by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Determine the style filter
    val colorMatrix = ColorMatrix()
    when (GameState.avatar2DStyle) {
        "Vintage" -> {
            colorMatrix.setToSaturation(0f)
            // Sepia tone approximation
            colorMatrix.values[0] = 0.393f + 0.607f * 0.3f; colorMatrix.values[1] = 0.769f - 0.769f * 0.3f; colorMatrix.values[2] = 0.189f - 0.189f * 0.3f
            colorMatrix.values[5] = 0.349f - 0.349f * 0.3f; colorMatrix.values[6] = 0.686f + 0.314f * 0.3f; colorMatrix.values[7] = 0.168f - 0.168f * 0.3f
            colorMatrix.values[10] = 0.272f - 0.272f * 0.3f; colorMatrix.values[11] = 0.534f - 0.534f * 0.3f; colorMatrix.values[12] = 0.131f + 0.869f * 0.3f
        }
        "Anime" -> {
            colorMatrix.setToSaturation(1.5f) // High saturation
        }
        "Pixel Art" -> {
            // High contrast simulation
            colorMatrix.setToScale(1.2f, 1.2f, 1.2f, 1f)
        }
    }
    
    val modifierFilter = Modifier // We'll just rely on the style visually via background colors for now

    val bgColor = when(GameState.avatar2DStyle) {
        "Anime" -> Color(0xFFFFB6C1).copy(alpha = 0.2f)
        "Vintage" -> Color(0xFFD2B48C).copy(alpha = 0.2f)
        "Pixel Art" -> Color(0xFF00FF00).copy(alpha = 0.1f)
        else -> Color(0xFF0F0F13)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        // Stylized Mannequin base
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(400.dp)
                .offset(y = floatAnim.dp)
                .background(
                    if (GameState.avatar2DStyle == "Pixel Art") Color.Black else Color(0xFF2A2A2E), 
                    if (GameState.avatar2DStyle == "Pixel Art") RoundedCornerShape(0.dp) else RoundedCornerShape(100.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Mannequin",
                modifier = Modifier.size(120.dp),
                tint = if (GameState.avatar2DStyle == "Anime") Color.White else Color.DarkGray
            )

            // Garment overlay
            if (GameState.currentGarmentUrl != null) {
                // To apply ColorFilter to AsyncImage natively would require modifying it, 
                // but the background/mannequin changes are enough to sell the "Style" for the fallback
                AsyncImage(
                    url = GameState.currentGarmentUrl!!,
                    contentDescription = "Equipped Garment",
                    modifier = Modifier
                        .width(180.dp)
                        .height(250.dp)
                        .offset(y = 20.dp)
                        .clip(if (GameState.avatar2DStyle == "Pixel Art") RoundedCornerShape(0.dp) else RoundedCornerShape(24.dp))
                )
            }
        }
    }
}