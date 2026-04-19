package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

@Composable
fun TutorialScreen(onNavigateToMain: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF4B0082))
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "DressYourself!",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF00FFFF),
                    shadow = Shadow(color = Color(0xFF00FFFF), blurRadius = 15f)
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Complete this tutorial to earn 20 Style Points!",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFD700)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFF252542).copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    TutorialStep("1", "Upload photos of your clothes to build your Virtual Wardrobe.")
                    Spacer(modifier = Modifier.height(16.dp))
                    TutorialStep("2", "Our local AI vision model analyzes your wardrobe in real-time.")
                    Spacer(modifier = Modifier.height(16.dp))
                    TutorialStep("3", "Get personalized outfits, compete in Runways, and earn badges!")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Note: Ensure you have Ollama running locally with a vision model (e.g., llava or qwen-vl) on port 11434.",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    GameState.addAction("Tutorial Complete", 20)
                    onNavigateToMain()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493))
            ) {
                Text("LET'S GET STARTED", color = Color.White, style = MaterialTheme.typography.button)
            }
        }
    }
}

@Composable
fun TutorialStep(number: String, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = number,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFF1493),
                shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 8f)
            ),
            modifier = Modifier.width(32.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }
}
