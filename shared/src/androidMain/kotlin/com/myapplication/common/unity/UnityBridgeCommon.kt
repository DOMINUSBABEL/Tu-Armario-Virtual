package com.myapplication.common.unity

import com.myapplication.common.gamification.GameState

actual fun sendTextureTo3DEngine(textureData: String, isUserUpload: Boolean) {
    // 2D Fallback: Update state instead of calling WebGL
    GameState.currentGarmentUrl = textureData
}

actual fun exportAvatarSnapshot() {
    // 2D Fallback: Use the current garment or a placeholder
    GameState.lastSnapshot = GameState.currentGarmentUrl ?: "file:///android_asset/garments/10075404.jpg"
    GameState.isSnapshotReady = true
}
