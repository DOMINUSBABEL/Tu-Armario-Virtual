package com.myapplication.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.myapplication.common.api.OllamaClient
import com.myapplication.common.image.ImagePicker
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    var showImagePicker by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var aiSuggestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val ollamaClient = remember { OllamaClient() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Wardrobe", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showImagePicker = true }) {
            Text("Upload Clothes Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedImageBytes != null) {
            Text("Image selected successfully.")
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = true
                    aiSuggestion = ""
                    coroutineScope.launch {
                        val response = ollamaClient.getOutfitSuggestion(selectedImageBytes!!)
                        aiSuggestion = response
                        isLoading = false
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

    ImagePicker(
        showPicker = showImagePicker,
        onImagePicked = { bytes ->
            if (bytes != null) {
                selectedImageBytes = bytes
            }
        },
        onPickerClosed = {
            showImagePicker = false
        }
    )
}
