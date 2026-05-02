package com.myapplication.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun UnityView(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E24)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "UNITY 3D ENGINE (iOS STUB)\nWaiting for Framework",
            color = Color(0xFF00FF00),
            fontWeight = FontWeight.Bold
        )
    }
}
