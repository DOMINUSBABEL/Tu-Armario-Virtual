package com.myapplication.common.ui.components

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun UnityView(modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                setBackgroundColor(AndroidColor.parseColor("#0F0F13"))
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
                settings.domStorageEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                
                webViewClient = WebViewClient()
                
                // Load the local HTML file that runs Three.js and the GLB model
                loadUrl("file:///android_asset/avatar_renderer.html")
                
                // Store a global reference so UnityBridge can access it
                com.myapplication.common.unity.UnityBridge.activeWebView = this
            }
        },
        update = { view ->
            // Update logic if needed
        }
    )
}
