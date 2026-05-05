package com.myapplication.common.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.myapplication.common.model.WardrobeItem

@Composable
fun DigitalVitrine(
    items: List<WardrobeItem>,
    modifier: Modifier = Modifier,
    preferences: List<String> = emptyList() // tags to prefer
) {
    // Logica de recomendación
    var displayedItems by remember { mutableStateOf<List<WardrobeItem>>(emptyList()) }
    
    // Simulate "throwing" or progressively showing the options based on preferences
    LaunchedEffect(items, preferences) {
        val sortedItems = if (preferences.isNotEmpty()) {
            items.sortedByDescending { item -> 
                preferences.count { pref -> 
                    item.category.contains(pref, ignoreCase = true) || 
                    item.brand.contains(pref, ignoreCase = true) 
                }
            }
        } else {
            items
        }
        
        displayedItems = emptyList()
        // "Arrojar" prendas una por una
        sortedItems.forEach { item ->
            delay(150)
            displayedItems = displayedItems + item
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFFF5F5F5))) { // Neutral background
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(displayedItems, key = { it.id }) { item ->
                VitrineCard(item)
            }
        }
    }
}

@Composable
fun VitrineCard(item: WardrobeItem) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -it }, 
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        ) + fadeIn(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f),
            shape = RoundedCornerShape(12.dp),
            elevation = 8.dp,
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        url = item.imageUrl,
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                    )
                }
                // Commit 17: Sponsored Cards UI
                if (item.brand == "Vélez" || item.brand == "Arturo Calle") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE0E5EC))
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("SPONSORED", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
                
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    color = Color(0xFF2A2A2E)
                )
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Gray
                )
            }
        }
    }
}
