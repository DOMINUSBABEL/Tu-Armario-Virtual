package com.dy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiscoverScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { AppNavigation(currentScreen, onNavigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // AI Assistant Header
            IsaAvatar(
                message = "¡Hola! Soy Isa. He analizado el clima de hoy y las nuevas colecciones regionales. ¿Te gustaría ver recomendaciones generadas con Gemini?"
            )
            
            // Content
            LazyColumn(
                contentPadding = PaddingValues(horizontal = IsaDimens.MarginMobile, vertical = IsaDimens.BaseSpacing)
            ) {
                item {
                    Text(
                        text = "Tendencias en tu región",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = IsaDimens.Gutter)
                    )
                }
                
                items(3) { index ->
                    ItemCard(
                        title = "Chaqueta de Cuero Essential $index",
                        brand = "VÉLEZ",
                        price = "$ 399.000",
                        rating = 4,
                        onTryClick = { /* Launch VTO */ },
                        onBuyClick = { /* Affiliate link */ }
                    )
                    Spacer(modifier = Modifier.height(IsaDimens.Gutter))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(currentScreen: String, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentScreen == "Discover",
            onClick = { onNavigate("Discover") },
            icon = { Text("🌍") },
            label = { Text("Descubrir", style = MaterialTheme.typography.labelMedium) }
        )
        NavigationBarItem(
            selected = currentScreen == "Wardrobe",
            onClick = { onNavigate("Wardrobe") },
            icon = { Text("👔") },
            label = { Text("Armario", style = MaterialTheme.typography.labelMedium) }
        )
        NavigationBarItem(
            selected = currentScreen == "IsaAssistant",
            onClick = { onNavigate("IsaAssistant") },
            icon = { Text("✨") },
            label = { Text("Isa AI", style = MaterialTheme.typography.labelMedium) }
        )
    }
}
