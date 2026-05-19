package com.dy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.myapplication.common.api.NanoBananaClient
import kotlinx.coroutines.launch
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun WardrobeScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val nanoClient = remember { NanoBananaClient() }
    
    var isLoadingOutfit by remember { mutableStateOf(false) }
    var outfitDescription by remember { mutableStateOf("") }
    var outfitImageUrl by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { AppNavigation(currentScreen, onNavigate) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Open Camera / Image Picker */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir prenda")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = IsaDimens.MarginMobile)
        ) {
            Spacer(modifier = Modifier.height(IsaDimens.Gutter))
            Text(
                text = "Tu Armario",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Tus prendas digitalizadas y guardadas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(IsaDimens.Gutter))
            
            Button(
                onClick = {
                    isLoadingOutfit = true
                    coroutineScope.launch {
                        val response = nanoClient.getOutfitSuggestion(
                            imageBytesList = emptyList(), // Use local images here if we had them loaded
                            theme = "Casual/Diario (Outfit del Día)",
                            itemDescriptions = listOf("Camisa Básica", "Jean Azul") // mock existing items
                        )
                        outfitDescription = response.description
                        outfitImageUrl = response.imageUrl
                        isLoadingOutfit = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLoadingOutfit) "Generando..." else "✨ Obtener Outfit del Día ✨")
            }
            
            if (outfitDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Recomendación:", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (outfitImageUrl != null) {
                            KamelImage(
                                resource = asyncPainterResource(data = outfitImageUrl!!),
                                contentDescription = "Outfit del Día",
                                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)).padding(bottom = 8.dp)
                            )
                        }
                        Text(outfitDescription, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(IsaDimens.Gutter))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(IsaDimens.BaseSpacing * 2),
                verticalArrangement = Arrangement.spacedBy(IsaDimens.BaseSpacing * 2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(6) { index ->
                    WardrobeItemCard(index)
                }
            }
        }
    }
}

@Composable
fun WardrobeItemCard(index: Int) {
    Card(
        shape = RoundedCornerShape(IsaDimens.RoundMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        modifier = Modifier.fillMaxWidth().aspectRatio(0.8f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Prenda $index",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Box(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Camisa Básica",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
