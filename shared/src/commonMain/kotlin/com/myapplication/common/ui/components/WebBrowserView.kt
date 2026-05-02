package com.myapplication.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebBrowserView(url: String, modifier: Modifier = Modifier)
