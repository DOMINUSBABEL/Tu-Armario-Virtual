package com.myapplication.common.gamification

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun StyleStatsBar() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFF252542)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${GameState.rankEmoji} Lvl ${GameState.fashionLevel} - ${GameState.title}",
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFF1493),
                        fontSize = 18.sp,
                        shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 8f)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${GameState.stylePoints} SP",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "💰 ${GameState.dyCoins} DY Coins",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700),
                            fontSize = 14.sp,
                            shadow = Shadow(color = Color(0xFFFFD700), blurRadius = 10f)
                        )
                    )
                }
            }
            if (GameState.styleStreak > 1) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Streak x${GameState.streakMultiplier} 🔥",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFF4500),
                            shadow = Shadow(color = Color(0xFFFF4500), blurRadius = 12f)
                        )
                    )
                    Text(
                        text = "${GameState.styleStreak} in a row",
                        style = MaterialTheme.typography.caption,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionNotification() {
    val message = GameState.lastActionMessage

    LaunchedEffect(message) {
        if (message != null) {
            delay(3000)
            GameState.clearMessage()
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = message != null,
            enter = slideInVertically(initialOffsetY = { -100 }) + fadeIn(tween(500)),
            exit = slideOutVertically(targetOffsetY = { -100 }) + fadeOut(tween(500))
        ) {
            Card(
                backgroundColor = Color(0xFF4B0082), // Deep Purple
                shape = RoundedCornerShape(16.dp),
                elevation = 12.dp
            ) {
                Text(
                    text = message ?: "",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    style = TextStyle(
                        color = Color(0xFF00FFFF), // Cyan neon
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 10f)
                    )
                )
            }
        }
    }
}
