package com.myapplication.common.unity

import com.myapplication.common.gamification.GameState

actual fun sendTextureTo3DEngine(textureData: String, isUserUpload: Boolean) {
    GameState.currentGarmentUrl = textureData
}

actual fun exportAvatarSnapshot() {
    GameState.lastSnapshot = GameState.currentGarmentUrl
    GameState.isSnapshotReady = true
}
