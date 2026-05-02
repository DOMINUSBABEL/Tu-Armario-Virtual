package com.myapplication.common.unity

actual fun sendTextureTo3DEngine(base64Texture: String) {
    UnityBridge.sendMessage("Avatar", "ApplyTexture", base64Texture)
}
