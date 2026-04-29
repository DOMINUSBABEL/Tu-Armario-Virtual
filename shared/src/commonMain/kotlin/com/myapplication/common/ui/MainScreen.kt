package com.myapplication.common.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    var analysisResult by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }

    // Minimalist, premium color palette
    val primaryPurple = Color(0xFF3B0B59) // Deeper, more elegant purple
    val accentPink = Color(0xFFE91E63) // Vibrant but sophisticated pink
    val surfaceWhite = Color(0xFFFFFFFF)
    val backgroundLight = Color(0xFFFAFAFC) // Very light gray-bone for contrast
    val textPrimary = Color(0xFF1C1C1E)
    val textSecondary = Color(0xFF8E8E93)

    Scaffold(
        backgroundColor = backgroundLight,
        bottomBar = {
            BottomNavigation(
                backgroundColor = surfaceWhite,
                elevation = 24.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = primaryPurple) },
                    label = { Text("Home", color = primaryPurple, fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                    selected = true,
                    onClick = { /* Current Screen */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = "Runway", tint = Color.LightGray) },
                    label = { Text("Runway", color = Color.LightGray, fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToRunway
                )
                BottomNavigationItem(
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Brush.linearGradient(listOf(accentPink, primaryPurple)), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Upload", tint = Color.White)
                        }
                    },
                    label = { },
                    selected = false,
                    onClick = { showImagePicker = true }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.LightGray) },
                    label = { Text("Profile", color = Color.LightGray, fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToLeaderboard
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp), // Extra padding for bottom bar
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gamification Header (Integrated, clean)
                GamificationHeader(primaryPurple, accentPink)
                
                Spacer(modifier = Modifier.height(24.dp))

                // Section Title: Editorial Feel
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                    Text(
                        text = "DressYourself",
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Serif, // Sophisticated touch
                        fontWeight = FontWeight.Bold,
                        color = textPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Curate your perfect style.",
                        fontSize = 14.sp,
                        color = textSecondary,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Scrollable Action Cards (Horizontal to save vertical space)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ActionCardMini(
                            title = "Wardrobe",
                            icon = Icons.Filled.List,
                            color = primaryPurple,
                            onClick = onNavigateToWardrobe
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Runway",
                            icon = Icons.Filled.Star,
                            color = accentPink,
                            onClick = onNavigateToRunway
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Ranks",
                            icon = Icons.Filled.ThumbUp,
                            color = primaryPurple,
                            onClick = onNavigateToLeaderboard
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Scan",
                            icon = Icons.Filled.Face,
                            color = primaryPurple,
                            onClick = { showImagePicker = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // New Section: Fashion Insights / Style Tip of the Day
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fashion Insights",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textPrimary
                        )
                        Text(
                            text = "See all",
                            fontSize = 14.sp,
                            color = accentPink,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { /* TODO: Navigate to blog/tips list */ }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Editorial Card
                    EditorialTipCard(primaryPurple, accentPink)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Analysis Section (Dynamic when image uploaded)
                if (selectedImageBytes != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(24.dp),
                        elevation = 4.dp,
                        backgroundColor = surfaceWhite
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text("Scanner Result", fontFamily = FontFamily.Serif, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = primaryPurple)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            if (isAnalyzing) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(color = accentPink, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Extracting styling properties...", color = textSecondary, fontSize = 14.sp)
                                }
                            } else if (analysisResult.isNotEmpty()) {
                                Text(analysisResult, color = textPrimary, fontSize = 14.sp, lineHeight = 20.sp)
                            } else {
                                Button(
                                    onClick = {
                                        isAnalyzing = true
                                        analysisResult = ""
                                        coroutineScope.launch {
                                            delay(2000)
                                            isAnalyzing = false
                                            analysisResult = "Garment: Charcoal Wool Blazer.\nVibe: Authority & Elegance.\nPair with: Crisp white shirt and tailored trousers to project maximum confidence."
                                            GameState.addAction("Visual Analysis", 30)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryPurple),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Analyze to build outfit", color = Color.White)
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
            onPickerClosed = { showImagePicker = false }
        )
    }
}

@Composable
fun EditorialTipCard(primaryColor: Color, accentColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { /* Expand Tip */ },
        elevation = 6.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Abstract background gradient (replacing an image for now)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFEFE7F8), Color(0xFFFDE9F0)),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
                        )
                    )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("STYLE TIP OF THE DAY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = primaryColor, letterSpacing = 1.sp)
                }
                
                // Content
                Column {
                    Text(
                        text = "The Power of Neutrals",
                        fontFamily = FontFamily.Serif,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "How to combine earthy tones to project an image of quiet authority and modern elegance.",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                }
                
                // Action
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Read Article", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = accentColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Filled.ArrowForward, contentDescription = "Read", tint = accentColor, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun ActionCardMini(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        elevation = 2.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color.copy(alpha = 0.08f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Medium, color = Color(0xFF1C1C1E), fontSize = 13.sp)
        }
    }
}

@Composable
fun GamificationHeader(primaryColor: Color, accentColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Rank Icon / Avatar placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(GameState.rankEmoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Lvl ${GameState.fashionLevel} — ${GameState.title}",
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    fontSize = 14.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                    Icon(Icons.Filled.Star, contentDescription = "SP", tint = accentColor, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("${GameState.stylePoints} SP", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
        
        // Coins
        Box(
            modifier = Modifier
                .background(Color(0xFFFFF9E6), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.ThumbUp, contentDescription = "Coins", tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${GameState.dyCoins}", color = Color(0xFFB8860B), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
