package com.myapplication.common.social

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myapplication.common.gamification.GameState
import com.myapplication.common.ui.components.AsyncImage
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder
import com.myapplication.common.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SocialFeedScreen(onNavigateBack: () -> Unit) {
    var mockPosts by remember {
        mutableStateOf(
            listOf(
                PostData("1", "Cyberpunk Neon Nights", "user_123", listOf("Cyberpunk", "Neon", "Temu")),
                PostData("2", "Minimalist Elegance", "fashion_guru", listOf("Minimalist", "Elegant", "Shein")),
                PostData("3", "Y2K Throwback Outfit", "retro_lover", listOf("Y2K", "Vintage", "Thrifted"))
            )
        )
    }

    LaunchedEffect(GameState.lastSnapshot) {
        val snapshot = GameState.lastSnapshot
        if (snapshot != null) {
            val newPost = PostData(
                id = "my_post_${System.currentTimeMillis()}",
                title = "My DY 3D Outfit",
                author = "me",
                tags = listOf("OOTD", "DY_Avatar", "SOTA"),
                imageDataUrl = snapshot
            )
            mockPosts = listOf(newPost) + mockPosts
            GameState.lastSnapshot = null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo Orgánico (Unity 3D Avatar mostrando el feed)
        UnityViewPlaceholder()

        // 2. Capa Flotante de UI
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                GlassPanel(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    alpha = 0.5f,
                    cornerRadius = 24.dp
                ) {
                    TopAppBar(
                        title = { Text("Fashion Feed", style = MaterialTheme.typography.h3, color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp)
            ) {
                items(mockPosts) { post ->
                    FeedItemGlass(post)
                }
            }
        }
    }
}

data class PostData(val id: String, val title: String, val author: String, val tags: List<String>, val imageDataUrl: String? = null)

@Composable
fun FeedItemGlass(post: PostData) {
    var isLiked by remember { mutableStateOf(false) }
    var showBigHeart by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val heartScale by animateFloatAsState(
        targetValue = if (showBigHeart) 1.5f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
    )
    val heartAlpha by animateFloatAsState(
        targetValue = if (showBigHeart) 0.8f else 0f,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp) // Simulated full screen height for the feed item
            .padding(bottom = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        isLiked = true
                        showBigHeart = true
                        coroutineScope.launch {
                            delay(800)
                            showBigHeart = false
                        }
                    }
                )
            }
    ) {
        // Invisible touch area or 3D background
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)))

        // Display user generated snapshot
        if (post.imageDataUrl != null) {
            AsyncImage(
                url = post.imageDataUrl,
                contentDescription = post.title,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Big Heart Animation
        if (showBigHeart || heartAlpha > 0f) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Big Heart",
                tint = NeonPeach.copy(alpha = heartAlpha),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(120.dp)
                    .scale(heartScale)
            )
        }

        // Glassmorphism info panel
        GlassPanel(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            alpha = 0.7f,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(post.title, style = MaterialTheme.typography.h6, color = Color.White, fontWeight = FontWeight.Bold)
                Text("@${post.author}", style = MaterialTheme.typography.body2, color = HolographicSilver)
                
                Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    post.tags.forEach { tag ->
                        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(NeonPeach.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                            Text("#$tag", color = NeonPeach, style = MaterialTheme.typography.caption)
                        }
                    }
                }
            }
        }

        // Actions (Right column)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { isLiked = !isLiked },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.6f))
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) NeonPeach else Color.White
                )
            }
            IconButton(
                onClick = { /* Share */ },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.6f))
            ) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
            }
            IconButton(
                onClick = { /* Add to wardrobe or Buy from E-commerce */ },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.6f))
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Get Outfit", tint = Color.White)
            }
        }
    }
}
