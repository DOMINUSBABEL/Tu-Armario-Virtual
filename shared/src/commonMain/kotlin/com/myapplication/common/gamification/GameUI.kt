package com.myapplication.common.gamification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun StyleStatsBar() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Lvl ${GameState.fashionLevel} - ${GameState.title}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "${GameState.stylePoints} SP",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
                )
            }
            if (GameState.styleStreak > 1) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Streak x${GameState.streakMultiplier} 🔥",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFA500) // Orange color
                    )
                    Text(
                        text = "${GameState.styleStreak} in a row",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
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
            enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut()
        ) {
            Card(
                backgroundColor = Color(0xFF4CAF50), // Green for success/reward
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Text(
                    text = message ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
