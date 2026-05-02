package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder
import com.myapplication.common.ui.components.AsyncImage
import com.myapplication.common.unity.sendTextureTo3DEngine

@Composable
fun ShopScreen(onNavigateBack: () -> Unit) {
    var selectedBrand by remember { mutableStateOf("All") }
    val brands = listOf("All", "Vélez", "Arturo Calle", "TRUE")

    val filteredItems = if (selectedBrand == "All") {
        com.myapplication.common.data.CatalogData.fullCatalog
    } else {
        com.myapplication.common.data.CatalogData.fullCatalog.filter { it.store == selectedBrand }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo Orgánico (Unity 3D Avatar en pose "Mirror")
        UnityViewPlaceholder()

        // 2. Capa Flotante de UI
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                GlassPanel(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    alpha = 0.5f,
                    cornerRadius = 24.dp
                ) {
                    TopAppBar(
                        title = { Text("Fast Fashion (3D)", style = MaterialTheme.typography.h3, color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                // Brand Filters
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(brands) { brand ->
                        FilterChipGlass(
                            text = brand,
                            isSelected = selectedBrand == brand,
                            onClick = { selectedBrand = brand }
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
                ) {
                    items(filteredItems) { item ->
                        ShopItemCardGlass(item)
                    }
                }
            }
        }
    }
}

data class ShopItem(val id: String, val name: String, val store: String, val price: Double, val image: String, val affiliateLink: String)

@Composable
fun ShopItemCardGlass(item: ShopItem) {
    GlassPanel(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { 
                // The WebViewAssetLoader expects https://appassets.androidplatform.net/assets/ for local assets
                val webViewUrl = item.image.replace("file:///android_asset/", "https://appassets.androidplatform.net/assets/")
                sendTextureTo3DEngine(webViewUrl)
            },
        alpha = 0.7f,
        cornerRadius = 16.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Real Image via AsyncImage
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.05f))
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    url = item.image,
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = item.store,
                        style = MaterialTheme.typography.overline,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = "$${item.price}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Buy Button
                Button(
                    onClick = { /* Open web browser with affiliate link */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(32.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Buy", modifier = Modifier.size(14.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Get on ${item.store}", fontSize = 10.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}
