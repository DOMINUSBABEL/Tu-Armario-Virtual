package com.myapplication.common.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun BatchImagePicker(
    onImagesPicked: (List<ByteArray>) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        onPickerClosed()
        val images = uris.mapNotNull { uri ->
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        }
        onImagesPicked(images)
    }

    LaunchedEffect(showPicker) {
        if (showPicker) {
            launcher.launch("image/*")
        }
    }
}
