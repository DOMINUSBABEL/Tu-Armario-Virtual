package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun BabylonWebView(
    modifier: Modifier,
    textureBase64: String?
) {
    Box(modifier = modifier.background(Color.LightGray), contentAlignment = Alignment.Center) {
        Text("3D View (WebView not supported on Desktop stub)")
    }
}
