package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.api.OllamaClient
import com.myapplication.common.db.DatabaseRepository
import kotlinx.coroutines.launch

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

    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF1A1A2E), Color(0xFF4B0082))
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        "The Runway",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFF1493),
                            shadow = Shadow(color = Color(0xFFFF1493), blurRadius = 15f)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(0xFF252542).copy(alpha = 0.9f),
                    elevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "CURRENT THEME \uD83C\uDF1F",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00FFFF),
                                letterSpacing = 2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = activeTheme,
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                shadow = Shadow(color = Color(0xFFFFD700), blurRadius = 20f)
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedButton(
                            onClick = { activeTheme = themes.random() },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent, contentColor = Color(0xFFFF1493))
                        ) {
                            Text("REROLL THEME", style = MaterialTheme.typography.button)
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
                                aiSuggestion = "Your wardrobe is empty! Upload some clothes first in the Main Screen."
                                isLoading = false
                                return@launch
                            }

                            val selectedItems = items.shuffled().take(3)
                            val images = selectedItems.map { it.imageBytes }
                            val itemDescriptions = selectedItems.map { "${it.color} ${it.category}" }

                            val response = ollamaClient.getOutfitSuggestion(images, activeTheme, itemDescriptions)
                            aiSuggestion = response
                            isLoading = false
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isLoading) Color.Gray else Color(0xFF00FFFF),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        if (isLoading) "Styling..." else "GENERATE OUTFIT \uD83E\uDE84",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Color(0xFFFF1493))
                }

                if (aiSuggestion.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color(0xFF252542).copy(alpha = 0.9f)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "Runway AI Suggestion",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD700)
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = aiSuggestion,
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = { onNavigateToShowcase(aiSuggestion, activeTheme) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF1493)),
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            "ENTER THE RUNWAY! \uD83D\uDC60\u2728",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        )
                    }
                }
            }
        }
    }
}
