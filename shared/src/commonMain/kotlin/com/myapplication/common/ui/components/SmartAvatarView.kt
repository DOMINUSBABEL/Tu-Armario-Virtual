package com.myapplication.common.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.gamification.GameState

@Composable
fun SmartAvatarView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        // Engine Router
        if (GameState.is3DMode) {
            UnityViewPlaceholder() // 3D WebGL Engine
        } else {
            Avatar2DView() // 2D Fallback Engine
        }

        // Floating Toggle & Style Menu
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 100.dp, end = 16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 3D/2D Toggle
            GlassPanel(
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp)
                    .clickable { GameState.is3DMode = !GameState.is3DMode },
                alpha = 0.8f,
                cornerRadius = 20.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "2D",
                        color = if (!GameState.is3DMode) MaterialTheme.colors.primary else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (!GameState.is3DMode) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                    )
                    Text(
                        "3D",
                        color = if (GameState.is3DMode) MaterialTheme.colors.primary else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (GameState.is3DMode) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                    )
                }
            }

            // 2D Style Selector (Only visible in 2D mode)
            if (!GameState.is3DMode) {
                val styles = listOf("Default", "Anime", "Pixel Art", "Vintage")
                styles.forEach { style ->
                    GlassPanel(
                        modifier = Modifier
                            .width(80.dp)
                            .height(30.dp)
                            .clickable { GameState.avatar2DStyle = style },
                        alpha = if (GameState.avatar2DStyle == style) 0.9f else 0.5f,
                        cornerRadius = 15.dp
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = style,
                                color = if (GameState.avatar2DStyle == style) Color.Black else Color.White,
                                fontSize = 10.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
