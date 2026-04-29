package com.myapplication.common.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.db.DatabaseRepository
import com.myapplication.common.gamification.ActionNotification
import com.myapplication.common.gamification.GameState
import com.myapplication.common.image.ImagePicker
import kotlinx.coroutines.delay
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
    var analysisResult by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }

    // Colors
    val primaryPurple = Color(0xFF4B0082)
    val accentPink = Color(0xFFFF1493)
    val surfaceWhite = Color(0xFFFFFFFF)
    val backgroundLight = Color(0xFFF8F9FA)
    val glassColor = Color.White.copy(alpha = 0.85f)

    Scaffold(
        backgroundColor = backgroundLight,
        bottomBar = {
            BottomNavigation(
                backgroundColor = surfaceWhite,
                elevation = 16.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = primaryPurple) },
                    label = { Text("Home", color = primaryPurple, fontSize = 10.sp) },
                    selected = true,
                    onClick = { /* Already here */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = "Runway", tint = Color.Gray) },
                    label = { Text("Runway", color = Color.Gray, fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToRunway
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Upload", tint = accentPink) },
                    label = { Text("Upload", color = accentPink, fontSize = 10.sp) },
                    selected = false,
                    onClick = { showImagePicker = true }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.Gray) },
                    label = { Text("Profile", color = Color.Gray, fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToLeaderboard
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Background subtle gradient
            Box(modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEDE7F6), backgroundLight, surfaceWhite)
                )
            ))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header / Gamification Integrated
                GamificationHeader(primaryPurple, accentPink, glassColor)
                
                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Text(
                    text = "DressYourself Studio",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryPurple,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 24.dp).align(Alignment.Start)
                )
                Text(
                    text = "Discover your style.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 24.dp).align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Cards Grid
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        ActionCard(
                            title = "My Wardrobe",
                            subtitle = "Explore your clothes",
                            icon = Icons.Filled.List,
                            color = primaryPurple,
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToWardrobe
                        )
                        ActionCard(
                            title = "Join Runway",
                            subtitle = "Show off your style",
                            icon = Icons.Filled.Star,
                            color = primaryPurple,
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToRunway
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        ActionCard(
                            title = "Badges & Ranks",
                            subtitle = "Check your progress",
                            icon = Icons.Filled.ThumbUp,
                            color = primaryPurple,
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToLeaderboard
                        )
                        ActionCard(
                            title = "Scan Clothes",
                            subtitle = "AI Visual Analysis",
                            icon = Icons.Filled.AddCircle,
                            color = accentPink,
                            modifier = Modifier.weight(1f),
                            onClick = { showImagePicker = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Analysis Section (Replacing LLM)
                if (selectedImageBytes != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(24.dp),
                        elevation = 8.dp,
                        backgroundColor = glassColor
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text("Image Scanned", fontWeight = FontWeight.Bold, color = primaryPurple)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            if (isAnalyzing) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(color = accentPink, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Running visual analysis algorithm...", color = Color.Gray)
                                }
                            } else if (analysisResult.isNotEmpty()) {
                                Text("Analysis Result:", fontWeight = FontWeight.SemiBold, color = primaryPurple)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(analysisResult, color = Color.DarkGray, fontSize = 14.sp)
                            } else {
                                Button(
                                    onClick = {
                                        isAnalyzing = true
                                        analysisResult = ""
                                        coroutineScope.launch {
                                            // Simulate Computer Vision Algorithm delay
                                            delay(2500)
                                            isAnalyzing = false
                                            analysisResult = "Detected: Deep Blue Cotton Shirt.\nSuggested Pairings: Khaki Chinos, White Sneakers.\nStyle: Casual/Smart-Casual."
                                            GameState.addAction("Visual Analysis", 30)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryPurple),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Analyze Garment", color = Color.White)
                                }
                            }
                        }
                    }
                }
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
                }
            },
            onPickerClosed = {
                showImagePicker = false
            }
        )
    }
}

@Composable
fun GamificationHeader(primaryColor: Color, accentColor: Color, glassColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .shadow(12.dp),
        backgroundColor = glassColor,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Lvl ${GameState.fashionLevel} - ${GameState.title}",
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = "SP", tint = accentColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${GameState.stylePoints} SP", color = Color.Gray, fontSize = 14.sp)
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(Icons.Filled.ThumbUp, contentDescription = "Coins", tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${GameState.dyCoins} Coins", color = Color.Gray, fontSize = 14.sp)
                }
            }
            
            // Progress Ring
            val progress = (GameState.stylePoints % 100) / 100f
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(60.dp)) {
                CircularProgressIndicator(
                    progress = 1f,
                    color = primaryColor.copy(alpha = 0.2f),
                    strokeWidth = 6.dp,
                    modifier = Modifier.fillMaxSize()
                )
                CircularProgressIndicator(
                    progress = progress,
                    color = accentColor,
                    strokeWidth = 6.dp,
                    modifier = Modifier.fillMaxSize()
                )
                Text(GameState.rankEmoji, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(24.dp))
            }
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF2C2C2C), fontSize = 16.sp)
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
