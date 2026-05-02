package com.myapplication.common.unity

import android.webkit.WebView

/**
 * Bridge to communicate with the 3D Engine (Three.js WebView) from Kotlin Multiplatform.
 */
object UnityBridge {
    
    // Reference to the active WebView, injected by UnityView.kt
    var activeWebView: WebView? = null

    /**
     * Sends a message to apply a texture to the 3D model.
     * 
     * @param gameObjectName Ignored in WebGL version.
     * @param methodName Ignored in WebGL version.
     * @param message The string payload (e.g., Base64 texture data).
     * @param isUserUpload Boolean to trigger background removal inside WebGL canvas.
     */
    fun sendMessage(gameObjectName: String, methodName: String, message: String, isUserUpload: Boolean = false) {
        try {
            activeWebView?.post {
                activeWebView?.evaluateJavascript("window.applyGarmentTexture('$message', $isUserUpload);", null)
                println("UnityBridge: Texture sent to 3D Canvas.")
            } ?: println("UnityBridge Error: WebView is not active.")
        } catch (e: Exception) {
            println("UnityBridge Error: Failed to execute JS. Error: ${e.message}")
        }
    }

    fun exportSnapshot() {
        try {
            activeWebView?.post {
                activeWebView?.evaluateJavascript("window.exportAvatarSnapshot();", null)
                println("UnityBridge: Exporting snapshot...")
            }
        } catch (e: Exception) {
            println("UnityBridge Error: Failed to export snapshot. Error: ${e.message}")
        }
    }
}
