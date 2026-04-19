package com.myapplication.common.image

import androidx.compose.runtime.Composable
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
actual fun ImagePicker(
    onImagePicked: (ByteArray?) -> Unit,
    showPicker: Boolean,
    onPickerClosed: () -> Unit
) {
    if (showPicker) {
        val fileChooser = JFileChooser()
        val filter = FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "webp")
        fileChooser.fileFilter = filter

        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val file: File = fileChooser.selectedFile
            try {
                val bytes = file.readBytes()
                onImagePicked(bytes)
            } catch (e: Exception) {
                e.printStackTrace()
                onImagePicked(null)
            }
        } else {
            onImagePicked(null)
        }
        onPickerClosed()
    }
}
