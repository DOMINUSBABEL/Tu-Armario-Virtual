package com.myapplication.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    alpha: Float = 0.4f,
    content: @Composable BoxScope.() -> Unit
) {
    // Glassmorphism with Neon glowing edges
    val neonGradient = Brush.linearGradient(
        colors = listOf(
            ElectricCyan.copy(alpha = 0.5f),
            DeepPurple.copy(alpha = 0.3f),
            Color.Transparent,
            Color.White.copy(alpha = 0.1f)
        )
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = DeepPurple,
                spotColor = ElectricCyan
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color(0xFF0F0F13).copy(alpha = alpha))
            .border(
                width = 1.dp,
                brush = neonGradient,
                shape = RoundedCornerShape(cornerRadius)
            ),
        content = content
    )
}
