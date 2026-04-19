package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
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
import com.myapplication.common.db.DatabaseRepository
import com.myapplication.db.WardrobeItem

@Composable
fun WardrobeScreen(onNavigateBack: () -> Unit) {
    var items by remember { mutableStateOf<List<WardrobeItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        items = DatabaseRepository.getAllWardrobeItems()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF252542))
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        "My Wardrobe \uD83D\uDEAA",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFF1493),
                            shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 15f)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Your wardrobe is empty \uD83D\uDE22",
                            style = TextStyle(fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Go back and upload some clothes!",
                            style = TextStyle(fontSize = 16.sp, color = Color.White.copy(alpha = 0.7f))
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.85f)
                                .border(1.dp, Color(0xFF00FFFF).copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            backgroundColor = Color(0xFF1A1A2E).copy(alpha = 0.8f),
                            elevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(Color(0xFF4B0082).copy(alpha = 0.5f), RoundedCornerShape(40.dp))
                                        .border(2.dp, Color(0xFFFF1493), RoundedCornerShape(40.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("\uD83D\uDC58", fontSize = 40.sp)
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = item.category.uppercase(),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD700),
                                        letterSpacing = 1.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Color: ${item.color}",
                                    style = TextStyle(fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${item.imageBytes.size / 1024} KB",
                                    style = TextStyle(fontSize = 10.sp, color = Color(0xFF00FFFF).copy(alpha = 0.6f))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
