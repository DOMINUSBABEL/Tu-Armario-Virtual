package com.myapplication.common.image

import androidx.compose.runtime.Composable

@Composable
expect fun ImagePicker(
    onImagePicked: (ByteArray?) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
)
