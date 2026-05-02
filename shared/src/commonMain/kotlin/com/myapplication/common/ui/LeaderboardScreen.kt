package com.myapplication.common.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan
import com.myapplication.common.ui.theme.NeonGold
import com.myapplication.common.ui.theme.NeonPeach
import com.myapplication.common.ui.theme.OnyxBlack
import androidx.compose.ui.graphics.Brush

data class RankEntry(val rank: Int, val name: String, val points: Int, val isCurrentUser: Boolean = false)

@Composable
fun LeaderboardScreen(onNavigateBack: () -> Unit) {
    val currentUserPoints = GameState.stylePoints

    // Create a mock leaderboard
    val allEntries = listOf(
        RankEntry(1, "Anna Wintour", 10000),
        RankEntry(2, "Ralph Lauren", 8500),
        RankEntry(3, "Coco Chanel", 7200),
        RankEntry(4, "Giorgio Armani", 5000),
        RankEntry(5, "You (Current)", currentUserPoints, true),
        RankEntry(6, "Local Fashionista", 120),
        RankEntry(7, "Style Newbie", 50)
    ).sortedByDescending { it.points }.mapIndexed { index, entry -> entry.copy(rank = index + 1) }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(OnyxBlack, DeepPurple.copy(alpha = 0.2f), OnyxBlack)
    )

    Scaffold(
        modifier = Modifier.background(backgroundGradient),
        backgroundColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Rankings & Badges", style = MaterialTheme.typography.h3, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().background(backgroundGradient).padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Badges Section
            Text("Your Badges", style = MaterialTheme.typography.h3, color = NeonPeach)
            Spacer(modifier = Modifier.height(16.dp))

            GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 24.dp) {
                val badges = GameState.unlockedBadges
                if (badges.isEmpty()) {
                    Text(
                        "Complete actions to earn badges!",
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.padding(24.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        badges.forEach { badge ->
                            Surface(
                                modifier = Modifier.padding(4.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = DeepPurple.copy(alpha = 0.4f)
                            ) {
                                Text(badge, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Leaderboard Section
            Text("Global Leaderboard", style = MaterialTheme.typography.h3, color = ElectricCyan)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(allEntries) { entry ->
                    val isTop3 = entry.rank <= 3
                    val rankColor = if (isTop3) NeonGold else Color.White.copy(alpha = 0.5f)

                    GlassPanel(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        alpha = if (entry.isCurrentUser) 0.8f else 0.4f,
                        cornerRadius = 16.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "#${entry.rank}",
                                    style = MaterialTheme.typography.h3,
                                    modifier = Modifier.width(48.dp),
                                    color = rankColor
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = entry.name,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = if (entry.isCurrentUser) NeonPeach else Color.White
                                )
                            }
                            Text(
                                text = "${entry.points} SP",
                                style = MaterialTheme.typography.h3,
                                color = if (entry.isCurrentUser) NeonPeach else ElectricCyan
                            )
                        }
                    }
                }
            }
        }
    }
}
