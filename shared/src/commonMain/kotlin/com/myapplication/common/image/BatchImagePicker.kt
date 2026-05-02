package com.myapplication.common.image

import androidx.compose.runtime.Composable

@Composable
expect fun BatchImagePicker(
    onImagesPicked: (List<ByteArray>) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
)
