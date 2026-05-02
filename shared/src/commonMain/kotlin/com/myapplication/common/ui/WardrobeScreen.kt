package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.myapplication.common.data.MockData
import com.myapplication.common.model.WardrobeItem
import com.myapplication.common.image.BatchImagePicker
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder
import com.myapplication.common.ui.components.bouncingClickable
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan
import com.myapplication.common.ui.theme.OnyxBlack
import androidx.compose.ui.graphics.Brush
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

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo Orgánico (Unity 3D Avatar)
        UnityViewPlaceholder()

        val backgroundGradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, DeepPurple.copy(alpha = 0.1f), OnyxBlack.copy(alpha = 0.9f))
        )

        // 2. UI Flotante de Cristal
        Scaffold(
            modifier = Modifier.background(backgroundGradient),
            backgroundColor = Color.Transparent,
            topBar = {
                GlassPanel(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    alpha = 0.5f,
                    cornerRadius = 24.dp
                ) {
                    TopAppBar(
                        title = { Text("Wardrobe / Fits", style = MaterialTheme.typography.h3, color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        actions = {
                            TextButton(onClick = { showBatchPicker = true }) {
                                Text("Upload Batch", color = MaterialTheme.colors.primary)
                            }
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Bottom // Push UI to the bottom so Avatar is visible
            ) {
                // Filters (Glass)
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChipGlass(
                            text = category,
                            isSelected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                    }
                }

                // Bottom Carousel for Selection (Glass panel container)
                GlassPanel(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    alpha = 0.6f,
                    cornerRadius = 24.dp
                ) {
                    Column {
                        Text(
                            text = "Tap to apply to Avatar",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
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
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.1f))
                                        .clickable {
                                            selectedTextureBase64 = Base64.encode(imageBytes)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Uploaded", color = Color.LightGray, style = MaterialTheme.typography.caption)
                                }
                            }

                            // Show mock data
                            items(filteredItems) { item ->
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colors.primary.copy(alpha = 0.2f))
                                        .clickable {
                                            // Simulate selection sending to Unity
                                            selectedTextureBase64 = null
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        item.name, 
                                        modifier = Modifier.padding(8.dp), 
                                        maxLines = 2, 
                                        color = Color.White,
                                        style = MaterialTheme.typography.caption
                                    )
                                }
                            }
                        }
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
fun FilterChipGlass(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor = if (isSelected) MaterialTheme.colors.primary else Color.White.copy(alpha = 0.1f)
    val textColor = if (isSelected) Color.Black else Color.White
    
    Box(
        modifier = Modifier
            .bouncingClickable(onClick = onClick)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.button
        )
    }
}