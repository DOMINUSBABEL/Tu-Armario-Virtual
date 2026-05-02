package com.myapplication.common.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun BabylonWebView(
    modifier: Modifier,
    textureBase64: String?
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                webViewClient = WebViewClient()
                loadUrl("file:///android_asset/babylon_scene.html")
                webView = this
            }
        },
        update = {
            if (textureBase64 != null) {
                it.evaluateJavascript("javascript:updateTexture('$textureBase64');", null)
            }
        }
    )
}
