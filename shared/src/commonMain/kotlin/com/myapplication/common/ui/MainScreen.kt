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
import com.myapplication.common.gamification.ActionNotification
import com.myapplication.common.gamification.GameState
import com.myapplication.common.gamification.StyleStatsBar
import com.myapplication.common.image.ImagePicker
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onNavigateToRunway: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showImagePicker by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var occasion by remember { mutableStateOf("") }
    var aiSuggestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val ollamaClient = remember { OllamaClient() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StyleStatsBar()
            Spacer(modifier = Modifier.height(16.dp))

            Text("DressYourself Studio", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = onNavigateToWardrobe) {
                    Text("My Wardrobe")
                }
                Button(onClick = onNavigateToRunway, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)) {
                    Text("Join Runway")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateToLeaderboard,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("View Badges & Rankings", color = MaterialTheme.colors.onSecondary)
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { showImagePicker = true }) {
                Text("Upload Clothes Image")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedImageBytes != null) {
                Text("Image selected successfully.", color = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = occasion,
                    onValueChange = { occasion = it },
                    label = { Text("Describe the Occasion (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isLoading = true
                        aiSuggestion = ""
                        coroutineScope.launch {
                            val response = ollamaClient.getOutfitSuggestion(listOf(selectedImageBytes!!), occasion)
                            aiSuggestion = response
                            isLoading = false
                            GameState.addAction("Outfit Generated", 50)
                        }
                    },
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Analyzing..." else "Get Outfit Suggestions")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator()
            }

            if (aiSuggestion.isNotEmpty()) {
                Text(
                    text = "Suggestions:",
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

        ActionNotification()
    }

    ImagePicker(
        showPicker = showImagePicker,
        onImagePicked = { bytes ->
            if (bytes != null) {
                selectedImageBytes = bytes
                // Save to local database for the real wardrobe feature
                DatabaseRepository.insertWardrobeItem("Uncategorized", "Unknown", bytes)
                GameState.addAction("Clothes Uploaded", 15)
            } else {
                GameState.resetStreak()
            }
        },
        onPickerClosed = {
            showImagePicker = false
        }
    )
}
