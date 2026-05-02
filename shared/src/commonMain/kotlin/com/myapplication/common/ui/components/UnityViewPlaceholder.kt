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
fun UnityViewPlaceholder(modifier: Modifier = Modifier) {
    // This is a placeholder for the actual Unity UaaL AndroidView/UIKitView
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2A2A35)), // Dark slate fallback
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "[ Unity 3D Avatar Rendering Engine ]\n(Vitrina Orgánica)",
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
    }
}
