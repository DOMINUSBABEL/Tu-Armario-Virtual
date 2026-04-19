package com.myapplication.common.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.api.OllamaClient
import com.myapplication.common.db.DatabaseRepository
import com.myapplication.common.gamification.ActionNotification
import com.myapplication.common.gamification.GameState
import com.myapplication.common.gamification.StyleStatsBar
import com.myapplication.common.image.ImagePicker
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onNavigateToRunway: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showImagePicker by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var occasion by remember { mutableStateOf("") }
    var aiSuggestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val ollamaClient = remember { OllamaClient() }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF252542))
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StyleStatsBar()
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DressYourself Studio",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 15f)
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(
                    onClick = onNavigateToWardrobe,
                    modifier = Modifier.weight(1f).height(50.dp).padding(end = 8.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent, contentColor = Color(0xFF00FFFF))
                ) {
                    Text("Wardrobe", style = MaterialTheme.typography.button)
                }
                Button(
                    onClick = onNavigateToRunway,
                    modifier = Modifier.weight(1f).height(50.dp).padding(start = 8.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493))
                ) {
                    Text("Runway \uD83C\uDF1F", color = Color.White, style = MaterialTheme.typography.button)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToLeaderboard,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4B0082))
            ) {
                Text("View Badges & Rankings \uD83C\uDFC6", color = Color.White, style = MaterialTheme.typography.button)
            }
            Spacer(modifier = Modifier.height(32.dp))
            
            Divider(color = Color.White.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showImagePicker = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF252542))
            ) {
                Text("Upload Clothes Image \uD83D\uDCF8", color = Color.White, style = MaterialTheme.typography.button)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedImageBytes != null) {
                Text("Image selected successfully \u2728", color = Color(0xFF00FFFF), style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = occasion,
                    onValueChange = { occasion = it },
                    label = { Text("Describe the Occasion (Optional)", color = Color.White.copy(alpha = 0.6f)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFFF1493),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        textColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        isLoading = true
                        aiSuggestion = ""
                        coroutineScope.launch {
                            val response = ollamaClient.getOutfitSuggestion(listOf(selectedImageBytes!!), occasion)
                            aiSuggestion = response
                            isLoading = false
                            GameState.addAction("Outfit Generated", 50)
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth().height(60.dp).scale(if (!isLoading) pulseScale else 1f),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isLoading) Color.Gray else Color(0xFFFF1493),
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isLoading) "Analyzing..." else "GET OUTFIT SUGGESTIONS \uD83D\uDD2E", style = MaterialTheme.typography.button, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF00FFFF))
            }

            if (aiSuggestion.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF252542).copy(alpha = 0.9f),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "AI Suggestion \uD83E\uDD16\uD83D\uDC85",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = aiSuggestion,
                            style = MaterialTheme.typography.body1,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        ActionNotification()
    }

    ImagePicker(
        showPicker = showImagePicker,
        onImagePicked = { bytes ->
            if (bytes != null) {
                selectedImageBytes = bytes
                DatabaseRepository.insertWardrobeItem("Uncategorized", "Unknown", bytes)
                GameState.addAction("Clothes Uploaded", 15)
            } else {
                GameState.resetStreak()
            }
        },
        onPickerClosed = {
            showImagePicker = false
        }
    )
}
