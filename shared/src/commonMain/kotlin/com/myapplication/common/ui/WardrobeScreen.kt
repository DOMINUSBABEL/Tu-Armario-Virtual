package com.myapplication.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.myapplication.common.db.DatabaseRepository
import com.myapplication.db.WardrobeItem

// We need an expect function for ByteArray to ImageBitmap for Compose Multiplatform.
// Assuming the project has a way to display images, or we can use a placeholder if we can't decode it easily in commonMain.
// For now, let's display text details and try to show a placeholder or the actual image.

@Composable
fun WardrobeScreen(onNavigateBack: () -> Unit) {
    var items by remember { mutableStateOf<List<WardrobeItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        items = DatabaseRepository.getAllWardrobeItems()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("My Wardrobe", style = MaterialTheme.typography.h4)
            Button(onClick = onNavigateBack) {
                Text("Back")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        if (items.isEmpty()) {
            Text("Your wardrobe is empty. Go back and upload some clothes!")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                        elevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("ID: ${item.id}")
                            Text("Category: ${item.category}")
                            Text("Color: ${item.color}")
                            Text("Image size: ${item.imageBytes.size} bytes")
                        }
                    }
                }
            }
        }
    }
}
