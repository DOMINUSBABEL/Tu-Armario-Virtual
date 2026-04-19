package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.gamification.GameState

data class RankEntry(val rank: Int, val name: String, val points: Int, val isCurrentUser: Boolean = false)

@Composable
fun LeaderboardScreen(onNavigateBack: () -> Unit) {
    val currentUserPoints = GameState.stylePoints

    val allEntries = listOf(
        RankEntry(1, "Anna Wintour \uD83D\uDC51", 10000),
        RankEntry(2, "Ralph Lauren \uD83D\uDC8E", 8500),
        RankEntry(3, "Coco Chanel \uD83E\uDD47", 7200),
        RankEntry(4, "Giorgio Armani \uD83E\uDD48", 5000),
        RankEntry(5, "You (Current) \uD83D\uDCAB", currentUserPoints, true),
        RankEntry(6, "Local Fashionista \uD83C\uDF1F", 120),
        RankEntry(7, "Style Newbie \uD83C\uDF31", 50)
    ).sortedByDescending { it.points }.mapIndexed { index, entry -> entry.copy(rank = index + 1) }

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF4B0082))
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        "Rankings & Badges",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF00FFFF),
                            shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 10f)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Badges Section
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0xFF252542).copy(alpha = 0.8f),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Your Badges \uD83C\uDF96\uFE0F",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF1493),
                                shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 8f)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        val badges = GameState.unlockedBadges
                        if (badges.isEmpty()) {
                            Text("Complete actions to earn badges!", color = Color.White.copy(alpha = 0.6f))
                        } else {
                            @OptIn(ExperimentalLayoutApi::class)
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                badges.forEach { badge ->
                                    Surface(
                                        modifier = Modifier.padding(4.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF4B0082).copy(alpha = 0.5f),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00FFFF))
                                    ) {
                                        Text(
                                            text = badge,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            style = MaterialTheme.typography.caption,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(16.dp))

                // Leaderboard Section
                Text(
                    "Global Leaderboard \uD83C\uDF0D",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFD700),
                        shadow = Shadow(color = Color(0xFFFFD700), blurRadius = 15f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(allEntries) { entry ->
                        val (backgroundColor, textColor, glowColor) = when {
                            entry.isCurrentUser -> Triple(Color(0xFFFF1493).copy(alpha = 0.2f), Color(0xFFFF1493), Color(0xFFFF1493))
                            entry.rank == 1 -> Triple(Color(0xFFFFD700).copy(alpha = 0.15f), Color(0xFFFFD700), Color(0xFFFFD700))
                            entry.rank == 2 -> Triple(Color(0xC0C0C0).copy(alpha = 0.15f), Color(0xC0C0C0), Color(0xC0C0C0))
                            entry.rank == 3 -> Triple(Color(0xCD7F32).copy(alpha = 0.15f), Color(0xCD7F32), Color(0xCD7F32))
                            else -> Triple(Color(0xFF252542).copy(alpha = 0.6f), Color.White, Color.Transparent)
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .let {
                                    if (entry.isCurrentUser || entry.rank <= 3) {
                                        it.border(1.dp, glowColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                    } else {
                                        it
                                    }
                                },
                            shape = RoundedCornerShape(16.dp),
                            backgroundColor = backgroundColor,
                            elevation = if (entry.isCurrentUser) 8.dp else 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "#${entry.rank}",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = textColor,
                                            shadow = if (glowColor != Color.Transparent) Shadow(color = glowColor, blurRadius = 10f) else null
                                        ),
                                        modifier = Modifier.width(48.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = entry.name,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = if (entry.isCurrentUser || entry.rank <= 3) FontWeight.Bold else FontWeight.Medium,
                                            color = if (entry.isCurrentUser) Color.White else textColor
                                        )
                                    )
                                }
                                Text(
                                    text = "${entry.points} SP",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (entry.rank <= 3) textColor else Color(0xFF00FFFF),
                                        shadow = if (entry.rank <= 3) Shadow(color = textColor, blurRadius = 8f) else null
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
