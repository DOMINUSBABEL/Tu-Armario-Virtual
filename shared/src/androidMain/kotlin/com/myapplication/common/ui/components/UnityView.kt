package com.myapplication.common.ui.components

import android.graphics.Color as AndroidColor
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun UnityView(modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // In a fully integrated UaaL environment, this would be:
            // val unityPlayer = com.unity3d.player.UnityPlayer(context)
            // return unityPlayer
            
            // For now, we simulate the Unity surface so the app compiles
            // and the Glassmorphism UI can be tested over it.
            FrameLayout(context).apply {
                setBackgroundColor(AndroidColor.parseColor("#15151A"))
                
                val textView = TextView(context).apply {
                    text = "UNITY 3D RENDER SURFACE\n(Android UaaL Bridge Connected)"
                    setTextColor(AndroidColor.GREEN)
                    textSize = 18f
                    gravity = Gravity.CENTER
                }
                
                addView(textView, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                ))
            }
        },
        update = { view ->
            // Update logic if needed when Compose state changes
        }
    )
}
