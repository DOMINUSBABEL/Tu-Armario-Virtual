package com.myapplication.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapplication.common.api.OllamaClient
import com.myapplication.common.db.DatabaseRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

val themes = listOf("Cyberpunk", "Y2K", "Business Casual", "Streetwear", "Goth", "Vintage 80s")

@Composable
fun RunwayScreen(onNavigateBack: () -> Unit) {
    var activeTheme by remember { mutableStateOf(themes.random()) }
    var aiSuggestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()
    val ollamaClient = remember { OllamaClient() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Runway", style = MaterialTheme.typography.h4)
            Button(onClick = onNavigateBack) {
                Text("Back")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Daily Theme:", style = MaterialTheme.typography.h6)
        Text(activeTheme, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.primary)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { activeTheme = themes.random() }) {
            Text("Reroll Theme")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                isLoading = true
                aiSuggestion = ""
                coroutineScope.launch {
                    val items = DatabaseRepository.getAllWardrobeItems()
                    if (items.isEmpty()) {
                        aiSuggestion = "Tu armario está vacío. ¡Sube algo de ropa primero!"
                        isLoading = false
                        return@launch
                    }
                    
                    // Limit to 3 items to avoid memory issues with local LLM and base64 string limits
                    val selectedItems = items.shuffled().take(3)
                    val images = selectedItems.map { it.imageBytes }
                    val itemDescriptions = selectedItems.map { "${it.color} ${it.category}" }
                    
                    val response = ollamaClient.getOutfitSuggestion(images, activeTheme, itemDescriptions)
                    aiSuggestion = response
                    isLoading = false
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(if (isLoading) "Generating Outfit..." else "Generate Outfit for Theme")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (aiSuggestion.isNotEmpty()) {
            Text(
                text = "Runway AI Suggestion:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = aiSuggestion,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
