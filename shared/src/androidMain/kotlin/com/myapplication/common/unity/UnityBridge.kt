package com.myapplication.common.unity

/**
 * Bridge to communicate with Unity from Kotlin Multiplatform.
 */
object UnityBridge {
    
    /**
     * Sends a message to a GameObject in the Unity scene.
     * 
     * @param gameObjectName The name of the GameObject in Unity.
     * @param methodName The method to call on the MonoBehaviour attached to it.
     * @param message The string payload (e.g., Base64 texture data).
     */
    fun sendMessage(gameObjectName: String, methodName: String, message: String) {
        try {
            // Using reflection to avoid compilation errors if the .aar is not yet imported.
            // In a real environment with the .aar, use: UnityPlayer.UnitySendMessage(...)
            val unityPlayerClass = Class.forName("com.unity3d.player.UnityPlayer")
            val method = unityPlayerClass.getMethod("UnitySendMessage", String::class.java, String::class.java, String::class.java)
            method.invoke(null, gameObjectName, methodName, message)
            println("UnityBridge: Message sent successfully to $gameObjectName")
        } catch (e: Exception) {
            println("UnityBridge Error: UnityPlayer not found or method failed. Is the .aar imported? Error: ${e.message}")
        }
    }
}
