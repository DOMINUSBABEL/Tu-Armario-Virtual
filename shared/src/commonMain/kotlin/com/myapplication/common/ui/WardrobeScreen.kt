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
import com.myapplication.common.image.BatchImagePicker
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun WardrobeScreen(onNavigateBack: () -> Unit) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Tops", "Bottoms", "Shoes", "Outerwear")

    val filteredItems = if (selectedCategory == "All") {
        MockData.defaultWardrobe
    } else {
        MockData.defaultWardrobe.filter { it.category == selectedCategory }
    }

    var showBatchPicker by remember { mutableStateOf(false) }
    var uploadedImages by remember { mutableStateOf<List<ByteArray>>(emptyList()) }
    var selectedTextureBase64 by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("3D Fitting Room", style = MaterialTheme.typography.h3) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colors.onSurface)
                    }
                },
                actions = {
                    Button(onClick = { showBatchPicker = true }) {
                        Text("Upload Batch")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            
            // 3D Viewer Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black)
            ) {
                BabylonWebView(
                    modifier = Modifier.fillMaxSize(),
                    textureBase64 = selectedTextureBase64
                )
            }

            // Filters
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

            // Bottom Carousel for Selection
            Text(
                text = "Select a garment to fit",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Show uploaded images
                items(uploadedImages) { imageBytes ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                            .clickable {
                                selectedTextureBase64 = Base64.encode(imageBytes)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Uploaded", color = Color.DarkGray)
                    }
                }
                
                // Show mock data
                items(filteredItems) { item ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                            .clickable {
                                // Simulate selection
                                selectedTextureBase64 = null
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.name, modifier = Modifier.padding(8.dp), maxLines = 2)
                    }
                }
            }
        }
    }

    BatchImagePicker(
        onImagesPicked = { images ->
            if (images.isNotEmpty()) {
                uploadedImages = uploadedImages + images
            }
        },
        showPicker = showBatchPicker,
        onPickerClosed = { showBatchPicker = false }
    )
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
