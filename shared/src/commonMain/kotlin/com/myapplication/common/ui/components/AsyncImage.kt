package com.myapplication.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AsyncImage(url: String, contentDescription: String, modifier: Modifier = Modifier)
