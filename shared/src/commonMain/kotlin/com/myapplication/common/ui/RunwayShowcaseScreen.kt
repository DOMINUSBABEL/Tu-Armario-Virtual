package com.myapplication.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.gamification.GameState
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun RunwayShowcaseScreen(
    outfitDescription: String,
    theme: String,
    onNavigateBack: () -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var hasVoted by remember { mutableStateOf(false) }
    var showParticles by remember { mutableStateOf(false) }

    LaunchedEffect(hasVoted) {
        if (hasVoted) {
            GameState.addAction("Runway Showcase ($rating Stars)", rating * 10)
            val coinsEarned = rating * 5
            GameState.awardCoins(coinsEarned)
            
            if (rating == 5) {
                showParticles = true
                delay(3000)
                showParticles = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E))
    ) {
        if (showParticles) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                for (i in 0..100) {
                    val x = Random.nextFloat() * canvasWidth
                    val y = Random.nextFloat() * canvasHeight
                    val radius = Random.nextFloat() * 10f + 2f
                    val color = listOf(Color(0xFFFF1493), Color(0xFF4B0082), Color(0xFF00FFFF), Color(0xFFFFD700)).random()
                    drawCircle(
                        color = color.copy(alpha = 0.8f),
                        center = Offset(x, y),
                        radius = radius
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Showcase: $theme",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    shadow = Shadow(
                        color = Color(0xFFFF1493),
                        blurRadius = 15f
                    )
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFF252542),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Generated Outfit",
                        style = MaterialTheme.typography.h6,
                        color = Color(0xFFFF69B4)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = outfitDescription,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(
                visible = !hasVoted,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Rate this Outfit", style = MaterialTheme.typography.h5, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (rating >= i) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star $i",
                                tint = if (rating >= i) Color(0xFFFFD700) else Color.Gray,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable { rating = i }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { hasVoted = true },
                        enabled = rating > 0,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493))
                    ) {
                        Text("Submit Vote", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            AnimatedVisibility(
                visible = hasVoted,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Thanks for voting!",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00FFFF),
                            shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 10f)
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onNavigateBack,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4B0082))
                    ) {
                        Text("Back to Runway", color = Color.White)
                    }
                }
            }
        }
    }
}
