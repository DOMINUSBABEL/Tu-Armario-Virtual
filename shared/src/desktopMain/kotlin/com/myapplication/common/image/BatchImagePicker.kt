package com.myapplication.common.image

import androidx.compose.runtime.Composable
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
actual fun BatchImagePicker(
    onImagesPicked: (List<ByteArray>) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    if (showPicker) {
        val fileChooser = JFileChooser().apply {
            isMultiSelectionEnabled = true
            fileFilter = FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "webp")
        }

        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val files: Array<File> = fileChooser.selectedFiles
            try {
                val images = files.map { it.readBytes() }
                onImagesPicked(images)
            } catch (e: Exception) {
                e.printStackTrace()
                onImagesPicked(emptyList())
            }
        } else {
            onImagesPicked(emptyList())
        }
        onPickerClosed()
    }
}
