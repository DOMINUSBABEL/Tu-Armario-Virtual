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
import androidx.compose.ui.draw.scale
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
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder
import com.myapplication.common.ui.components.bouncingClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onNavigateToRunway: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToSocialFeed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showImagePicker by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var analysisResult by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo Orgánico (Unity 3D Avatar)
        UnityViewPlaceholder()

        val backgroundGradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, DeepPurple.copy(alpha = 0.15f), OnyxBlack.copy(alpha = 0.8f))
        )

        // 2. Capa Flotante de UI (Transparente)
        Scaffold(
            modifier = Modifier.background(backgroundGradient),
            backgroundColor = Color.Transparent,
            bottomBar = {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.85f),
                    elevation = 0.dp,
                    modifier = Modifier.background(Color.Transparent)
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
                        icon = { Icon(Icons.Filled.Face, contentDescription = "Social Feed", tint = Color.LightGray) },
                        label = { Text("Feed", color = Color.LightGray, fontSize = 10.sp) },
                        selected = false,
                        onClick = onNavigateToSocialFeed
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
                    // Gamification Header (Glass)
                    GamificationHeaderGlass()
                    
                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Title
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                        Text(
                            text = "DressYourself",
                            style = MaterialTheme.typography.h1
                        )
                        Text(
                            text = "Curate your perfect style.",
                            style = MaterialTheme.typography.body2,
                            color = HolographicSilver,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Scrollable Action Cards (Glassmorphism)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            ActionCardGlass(
                                title = "Feed",
                                icon = Icons.Filled.Face,
                                color = MaterialTheme.colors.secondary,
                                onClick = onNavigateToSocialFeed
                            )
                        }
                        item {
                            ActionCardGlass(
                                title = "Wardrobe",
                                icon = Icons.AutoMirrored.Filled.List,
                                color = MaterialTheme.colors.primary,
                                onClick = onNavigateToWardrobe
                            )
                        }
                        item {
                            ActionCardGlass(
                                title = "Runway",
                                icon = Icons.Filled.Star,
                                color = MaterialTheme.colors.secondary,
                                onClick = onNavigateToRunway
                            )
                        }
                        item {
                            ActionCardGlass(
                                title = "Ranks",
                                icon = Icons.Filled.ThumbUp,
                                color = MaterialTheme.colors.primary,
                                onClick = onNavigateToLeaderboard
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Dynamic Analysis Section (Glass)
                    if (selectedImageBytes != null) {
                        GlassPanel(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            alpha = 0.7f
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text("Scanner Result", style = MaterialTheme.typography.h3, color = Color.White)
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                if (isAnalyzing) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CircularProgressIndicator(color = MaterialTheme.colors.secondary, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text("Extracting styling properties...", style = MaterialTheme.typography.body2, color = Color.White)
                                    }
                                } else if (analysisResult.isNotEmpty()) {
                                    Text(analysisResult, style = MaterialTheme.typography.body1, color = Color.White)
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
}

@Composable
fun ActionCardGlass(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    GlassPanel(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp)
            .bouncingClickable(onClick = onClick),
        alpha = 0.5f
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Medium, color = Color.White, fontSize = 13.sp)
        }
    }
}

@Composable
fun GamificationHeaderGlass() {
    GlassPanel(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        alpha = 0.5f,
        cornerRadius = 24.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(NeonPeach.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(GameState.rankEmoji, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Lvl ${GameState.fashionLevel} — ${GameState.title}",
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                        Icon(Icons.Filled.Star, contentDescription = "SP", tint = HolographicSilver, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("${GameState.stylePoints} SP", style = MaterialTheme.typography.body2, color = HolographicSilver)
                    }
                }
            }
            
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ThumbUp, contentDescription = "Coins", tint = NeonPeach, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${GameState.dyCoins}", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
