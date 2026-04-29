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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.myapplication.common.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onNavigateToRunway: () -> Unit,
    onNavigateToShop: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showImagePicker by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var analysisResult by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 24.dp
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = MaterialTheme.colors.primary) },
                    label = { Text("Home", color = MaterialTheme.colors.primary, fontSize = 10.sp, fontWeight = FontWeight.Medium) },
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
                                .size(40.dp)
                                .background(Brush.linearGradient(listOf(MaterialTheme.colors.secondary, MaterialTheme.colors.primary)), RoundedCornerShape(12.dp)),
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
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Shop", tint = Color.LightGray) },
                    label = { Text("Shop", color = Color.LightGray, fontSize = 10.sp) },
                    selected = false,
                    onClick = onNavigateToShop
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
                GamificationHeader(MaterialTheme.colors.primary, MaterialTheme.colors.secondary)
                
                Spacer(modifier = Modifier.height(24.dp))

                // Section Title: Editorial Feel
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                    Text(
                        text = "DressYourself",
                        style = MaterialTheme.typography.h1
                    )
                    Text(
                        text = "Curate your perfect style.",
                        style = MaterialTheme.typography.body2,
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
                            icon = Icons.AutoMirrored.Filled.List,
                            color = MaterialTheme.colors.primary,
                            onClick = onNavigateToWardrobe
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Runway",
                            icon = Icons.Filled.Star,
                            color = MaterialTheme.colors.secondary,
                            onClick = onNavigateToRunway
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Ranks",
                            icon = Icons.Filled.ThumbUp,
                            color = MaterialTheme.colors.primary,
                            onClick = onNavigateToLeaderboard
                        )
                    }
                    item {
                        ActionCardMini(
                            title = "Scan",
                            icon = Icons.Filled.Face,
                            color = MaterialTheme.colors.primary,
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
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text(
                            text = "See all",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { /* TODO: Navigate to blog/tips list */ }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Editorial Card
                    EditorialTipCard(MaterialTheme.colors.primary, MaterialTheme.colors.secondary)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Analysis Section (Dynamic when image uploaded)
                if (selectedImageBytes != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(24.dp),
                        elevation = 4.dp,
                        backgroundColor = MaterialTheme.colors.surface
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text("Scanner Result", style = MaterialTheme.typography.h3)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            if (isAnalyzing) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(color = MaterialTheme.colors.secondary, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Extracting styling properties...", style = MaterialTheme.typography.body2)
                                }
                            } else if (analysisResult.isNotEmpty()) {
                                Text(analysisResult, style = MaterialTheme.typography.body1)
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
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
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
            // Abstract background gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFEFE7F8), Color(0xFFFDF2F8)),
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
                    Text("STYLE TIP OF THE DAY", style = MaterialTheme.typography.overline)
                }
                
                // Content
                Column {
                    Text(
                        text = "The Power of Neutrals",
                        style = MaterialTheme.typography.h2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "How to combine earthy tones to project an image of quiet authority and modern elegance.",
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Action
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Read Article", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = accentColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Read", tint = accentColor, modifier = Modifier.size(14.dp))
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
                    style = MaterialTheme.typography.subtitle1
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                    Icon(Icons.Filled.Star, contentDescription = "SP", tint = accentColor, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("${GameState.stylePoints} SP", style = MaterialTheme.typography.body2)
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
