package com.myapplication.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.data.MockData
import com.myapplication.common.model.WardrobeItem

@Composable
fun WardrobeScreen(onNavigateBack: () -> Unit) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Tops", "Bottoms", "Shoes", "Outerwear")

    val filteredItems = if (selectedCategory == "All") {
        MockData.defaultWardrobe
    } else {
        MockData.defaultWardrobe.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wardrobe", style = MaterialTheme.typography.h3) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colors.onSurface)
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            
            // Filters
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        text = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredItems) { item ->
                    WardrobeItemCard(item)
                }
            }
        }
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else Color.White
    val textColor = if (isSelected) Color.White else MaterialTheme.colors.onSurface
    val borderColor = if (isSelected) Color.Transparent else MaterialTheme.colors.onSurface.copy(alpha = 0.1f)

    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        elevation = if (isSelected) 4.dp else 0.dp,
        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, borderColor) else null
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.body2,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun WardrobeItemCard(item: WardrobeItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* View details */ },
        elevation = 2.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Image Placeholder area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFF0F0F0)) // Light gray placeholder
            ) {
                // In a real app, load the image via Coil/Kamel using item.imageUrl
                // For now, we simulate an image area
                Text(
                    text = "IMG",
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = item.brand,
                    style = MaterialTheme.typography.overline,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}
