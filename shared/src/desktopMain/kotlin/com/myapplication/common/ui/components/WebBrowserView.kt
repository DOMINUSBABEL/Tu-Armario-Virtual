package com.myapplication.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun WebBrowserView(url: String, modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Text("Desktop Web Browser Stub for: $url", color = Color.Black)
    }
}
