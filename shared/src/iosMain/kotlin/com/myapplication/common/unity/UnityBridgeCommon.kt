package com.myapplication.common.unity

actual fun sendTextureTo3DEngine(textureData: String, isUserUpload: Boolean) {
    println("iOS: Sending texture to 3D engine ignored (Stub).")
}

actual fun exportAvatarSnapshot() {
    println("iOS: Export snapshot ignored (Stub).")
}
