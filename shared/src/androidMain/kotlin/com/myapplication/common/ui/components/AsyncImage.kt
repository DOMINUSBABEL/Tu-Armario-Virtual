package com.myapplication.common.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
fun ShimmerAnimation(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color.LightGray.copy(alpha = 0.2f), Color.White.copy(alpha = 0.5f), Color.LightGray.copy(alpha = 0.2f)),
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Box(modifier = modifier.background(brush))
}

@Composable
actual fun AsyncImage(url: String, contentDescription: String, modifier: Modifier) {
    var bitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                if (url.startsWith("data:image")) {
                    val base64Data = url.substringAfter("base64,")
                    val decodedBytes = android.util.Base64.decode(base64Data, android.util.Base64.DEFAULT)
                    val decoded = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    bitmap = decoded?.asImageBitmap()
                } else {
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
                }
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
                ShimmerAnimation(modifier = Modifier.matchParentSize())
            } else {
                androidx.compose.material.Text("No Image", color = Color.LightGray)
            }
        }
    }
}
