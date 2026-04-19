package com.myapplication.common.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun ImagePicker(
    onImagePicked: (ByteArray?) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onPickerClosed()
        if (uri != null) {
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            onImagePicked(bytes)
        } else {
            onImagePicked(null)
        }
    }

    LaunchedEffect(showPicker) {
        if (showPicker) {
            launcher.launch("image/*")
        }
    }
}
