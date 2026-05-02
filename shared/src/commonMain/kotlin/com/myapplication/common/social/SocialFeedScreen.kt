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

@Composable
fun SocialFeedScreen(onNavigateBack: () -> Unit) {
    val mockPosts = remember {
        listOf(
            PostData("1", "Cyberpunk Neon Nights", "user_123", listOf("Cyberpunk", "Neon")),
            PostData("2", "Minimalist Elegance", "fashion_guru", listOf("Minimalist", "Elegant")),
            PostData("3", "Y2K Throwback Outfit", "retro_lover", listOf("Y2K", "Vintage"))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fashion Feed") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        // Using LazyColumn to simulate a Vertical Pager (Snap behavior is more complex in common KMP without custom modifiers, but this serves the feed purpose)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF0F0F13)), // Onyx Black
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(mockPosts) { post ->
                FeedItem(post)
            }
        }
    }
}

data class PostData(val id: String, val title: String, val author: String, val tags: List<String>)

@Composable
fun FeedItem(post: PostData) {
    var isLiked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
    ) {
        // Mock image/video background
        Box(modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.3f)))

        // Glassmorphism info panel
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f)) // Simulated glass blur
                .padding(16.dp)
        ) {
            Text(post.title, style = MaterialTheme.typography.h6, color = Color.White, fontWeight = FontWeight.Bold)
            Text("@${post.author}", style = MaterialTheme.typography.body2, color = Color(0xFFE0E5EC)) // Holographic Silver
            
            Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                post.tags.forEach { tag ->
                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFFFF7A9A).copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("#$tag", color = Color(0xFFFF7A9A), style = MaterialTheme.typography.caption) // Neon Peach
                    }
                }
            }
        }

        // Actions
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { isLiked = !isLiked },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color(0xFFFF7A9A) else Color.White
                )
            }
            IconButton(
                onClick = { /* Share */ },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
            }
            IconButton(
                onClick = { /* Add to wardrobe */ },
                modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Add to Wardrobe", tint = Color.White)
            }
        }
    }
}
