package com.myapplication.common.image

import androidx.compose.runtime.Composable

@Composable
actual fun ImagePicker(
    onImagePicked: (ByteArray?) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    // Basic stub for iOS to allow compilation.
    // Implementing a full iOS ImagePicker requires UIKit interop (UIImagePickerController).
    if (showPicker) {
        onImagePicked(null)
        onPickerClosed()
    }
}
