package com.myapplication.common.unity

actual fun sendTextureTo3DEngine(textureData: String, isUserUpload: Boolean) {
    UnityBridge.sendMessage("Avatar", "ApplyTexture", textureData, isUserUpload)
}

actual fun exportAvatarSnapshot() {
    UnityBridge.exportSnapshot()
}
