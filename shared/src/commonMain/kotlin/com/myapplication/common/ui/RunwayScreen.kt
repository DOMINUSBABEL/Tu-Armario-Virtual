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
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.bouncingClickable
import com.myapplication.common.ui.theme.DeepPurple
import com.myapplication.common.ui.theme.ElectricCyan
import com.myapplication.common.ui.theme.NeonPeach
import com.myapplication.common.ui.theme.OnyxBlack
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import kotlinx.coroutines.launch
import kotlin.random.Random

val themes = listOf("Cyberpunk", "Y2K", "Business Casual", "Streetwear", "Goth", "Vintage 80s")

@Composable
fun RunwayScreen(
    onNavigateBack: () -> Unit,
    onNavigateToShowcase: (String, String) -> Unit
) {
    var activeTheme by remember { mutableStateOf(themes.random()) }
    var aiSuggestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()
    val ollamaClient = remember { OllamaClient() }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(OnyxBlack, DeepPurple.copy(alpha = 0.2f), OnyxBlack)
    )

    Column(
        modifier = Modifier.fillMaxSize().background(backgroundGradient).padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text("Runway Challenge", style = MaterialTheme.typography.h2)
            Spacer(modifier = Modifier.width(48.dp)) // balance
        }

        Spacer(modifier = Modifier.height(24.dp))

        GlassPanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
            Column(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Current Theme", style = MaterialTheme.typography.subtitle1, color = ElectricCyan)
                Spacer(modifier = Modifier.height(8.dp))
                Text(activeTheme, style = MaterialTheme.typography.h1, color = NeonPeach)

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = { activeTheme = themes.random() },
                    modifier = Modifier.bouncingClickable(onClick = { activeTheme = themes.random() }),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                ) {
                    Text("REROLL THEME", color = Color.White)
                }
            }
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
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            modifier = Modifier.fillMaxWidth().height(56.dp).bouncingClickable(enabled = !isLoading, onClick = {})
        ) {
            Text(if (isLoading) "GENERATING OUTFIT..." else "GENERATE OUTFIT", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator(color = ElectricCyan)
        }

        if (aiSuggestion.isNotEmpty()) {
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "AI Suggestion",
                        style = MaterialTheme.typography.subtitle1,
                        color = ElectricCyan
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = aiSuggestion,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { onNavigateToShowcase(aiSuggestion, activeTheme) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = NeonPeach),
                        modifier = Modifier.fillMaxWidth().height(50.dp).bouncingClickable(onClick = {
                            onNavigateToShowcase(aiSuggestion, activeTheme)
                        })
                    ) {
                        Text("ENTER THE RUNWAY", color = Color.Black)
                    }
                }
            }
        }
    }
}
