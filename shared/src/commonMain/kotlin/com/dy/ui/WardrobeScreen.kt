package com.dy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun WardrobeScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
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
