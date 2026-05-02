package com.myapplication.common.image

import androidx.compose.runtime.Composable

@Composable
actual fun BatchImagePicker(
    onImagesPicked: (List<ByteArray>) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    if (showPicker) {
        onImagesPicked(emptyList())
        onPickerClosed()
    }
}
