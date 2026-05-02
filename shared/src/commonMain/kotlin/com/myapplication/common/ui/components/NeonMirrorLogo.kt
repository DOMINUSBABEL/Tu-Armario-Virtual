package com.myapplication.common.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan
import com.myapplication.common.ui.theme.NeonPeach

@Composable
fun NeonMirrorLogo(modifier: Modifier = Modifier, size: Dp = 100.dp) {
    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = size.toPx() * 0.05f
        val padding = size.toPx() * 0.15f

        // Mirror Frame (Tilted Rectangle)
        val frameWidth = size.toPx() - padding * 2
        val frameHeight = size.toPx() * 1.2f - padding * 2 // slightly taller than wide

        val gradientBrush = Brush.linearGradient(
            colors = listOf(ElectricCyan, DeepPurple, NeonPeach),
            start = Offset(0f, 0f),
            end = Offset(size.toPx(), size.toPx())
        )

        // Draw Outer Glow
        drawRoundRect(
            brush = gradientBrush,
            topLeft = Offset(padding, padding),
            size = Size(frameWidth, frameHeight),
            cornerRadius = CornerRadius(20f, 20f),
            style = Stroke(width = strokeWidth * 3, cap = StrokeCap.Round, join = StrokeJoin.Round),
            alpha = 0.3f
        )

        // Draw Main Frame
        drawRoundRect(
            brush = gradientBrush,
            topLeft = Offset(padding, padding),
            size = Size(frameWidth, frameHeight),
            cornerRadius = CornerRadius(20f, 20f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Draw reflection/sparkle in top right corner
        val sparklePath = Path().apply {
            val sparkX = padding + frameWidth * 0.8f
            val sparkY = padding + frameHeight * 0.2f
            val sparkSize = size.toPx() * 0.15f

            moveTo(sparkX, sparkY - sparkSize / 2)
            quadraticBezierTo(sparkX, sparkY, sparkX + sparkSize / 2, sparkY)
            quadraticBezierTo(sparkX, sparkY, sparkX, sparkY + sparkSize / 2)
            quadraticBezierTo(sparkX, sparkY, sparkX - sparkSize / 2, sparkY)
            quadraticBezierTo(sparkX, sparkY, sparkX, sparkY - sparkSize / 2)
            close()
        }

        drawPath(
            path = sparklePath,
            color = Color.White
        )
    }
}
