package com.myapplication.common.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStream

@Composable
actual fun AsyncImage(url: String, contentDescription: String, modifier: Modifier) {
    var bitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                val input: InputStream = if (url.startsWith("file:///android_asset/")) {
                    val assetPath = url.replace("file:///android_asset/", "")
                    context.assets.open(assetPath)
                } else {
                    val connection = URL(url).openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    connection.inputStream
                }
                val decoded = BitmapFactory.decodeStream(input)
                bitmap = decoded?.asImageBitmap()
                input.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFFE0E5EC))
            } else {
                androidx.compose.material.Text("No Image", color = Color.LightGray)
            }
        }
    }
}
