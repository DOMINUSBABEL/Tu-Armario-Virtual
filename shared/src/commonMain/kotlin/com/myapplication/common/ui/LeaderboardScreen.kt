package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rankings & Badges") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Badges Section
            Text("Your Badges", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            val badges = GameState.unlockedBadges
            if (badges.isEmpty()) {
                Text("Complete actions to earn badges!", color = Color.Gray)
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    badges.forEach { badge ->
                        Surface(
                            modifier = Modifier.padding(4.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                        ) {
                            Text(badge, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Spacer(modifier = Modifier.height(24.dp))

            // Leaderboard Section
            Text("Global Leaderboard", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(allEntries) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        backgroundColor = if (entry.isCurrentUser) MaterialTheme.colors.primary.copy(alpha = 0.1f) else MaterialTheme.colors.surface,
                        elevation = if (entry.isCurrentUser) 4.dp else 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "#${entry.rank}",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(40.dp),
                                    color = if (entry.rank <= 3) Color(0xFFFFD700) else Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = entry.name,
                                    fontWeight = if (entry.isCurrentUser) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                            Text("${entry.points} SP", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
