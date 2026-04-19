package com.myapplication.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    val infiniteTransition = rememberInfiniteTransition()
    val starScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0C29), Color(0xFF302B63), Color(0xFF24243E))
                )
            )
    ) {
        if (showParticles) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                for (i in 0..150) {
                    val x = Random.nextFloat() * canvasWidth
                    val y = Random.nextFloat() * canvasHeight
                    val radius = Random.nextFloat() * 12f + 3f
                    val color = listOf(Color(0xFFFF1493), Color(0xFF00FFFF), Color(0xFFFFD700)).random()
                    drawCircle(
                        color = color.copy(alpha = Random.nextFloat() * 0.8f + 0.2f),
                        center = Offset(x, y),
                        radius = radius
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Showcase:",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
            Text(
                text = theme.uppercase(),
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    shadow = Shadow(
                        color = Color(0xFFFF1493),
                        blurRadius = 25f
                    ),
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = Color(0xFF1A1A2E).copy(alpha = 0.85f),
                elevation = 16.dp,
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFF1493).copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "THE LOOK \uD83D\uDC85",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF00FFFF),
                            letterSpacing = 1.5.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = outfitDescription,
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 26.sp,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = !hasVoted,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "RATE THIS FIT",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (i in 1..5) {
                            val isSelected = rating >= i
                            Icon(
                                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star $i",
                                tint = if (isSelected) Color(0xFFFFD700) else Color.White.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .size(56.dp)
                                    .padding(4.dp)
                                    .clickable { rating = i }
                                    .scale(if (rating == i) starScale else 1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = { hasVoted = true },
                        enabled = rating > 0,
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493))
                    ) {
                        Text(
                            "SUBMIT VOTE",
                            color = Color.White,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                        )
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
                        text = "YOU SLAYED! \uD83D\uDD25",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF00FFFF),
                            shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 20f)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Earned ${rating * 10} SP & ${rating * 5} Coins",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = onNavigateBack,
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4B0082))
                    ) {
                        Text(
                            "BACK TO MAIN",
                            color = Color.White,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                        )
                    }
                }
            }
        }
    }
}
