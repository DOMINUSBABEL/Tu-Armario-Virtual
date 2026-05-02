package com.myapplication.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun BabylonWebView(
    modifier: Modifier = Modifier,
    textureBase64: String? = null
)
