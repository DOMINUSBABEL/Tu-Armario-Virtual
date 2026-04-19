package com.myapplication.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TutorialScreen(onNavigateToMain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to AI Outfit Recommender!",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "1. Upload photos of your clothes.\n" +
                   "2. Our local AI vision model will analyze your wardrobe.\n" +
                   "3. Get personalized outfit suggestions automatically!",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Note: Ensure you have Ollama running locally with a vision model (e.g., llava or qwen-vl) on port 11434 for the best privacy-friendly experience.",
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNavigateToMain,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Let's Get Started")
        }
    }
}
