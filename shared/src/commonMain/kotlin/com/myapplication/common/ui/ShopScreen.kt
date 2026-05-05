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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.ui.components.GlassPanel
// import com.myapplication.common.ui.components.UnityViewPlaceholder
import com.myapplication.common.ui.components.AsyncImage
import com.myapplication.common.unity.sendTextureTo3DEngine
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.myapplication.common.ui.components.WebBrowserView
import androidx.compose.material.icons.filled.Search
import kotlinx.coroutines.launch

@Composable
fun ShopScreen(onNavigateBack: () -> Unit) {
    var selectedBrand by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val brands = listOf("All", "Vélez", "Arturo Calle", "TRUE", "H&M")
    
    // State for In-App Browser
    var browserUrl by remember { mutableStateOf<String?>(null) }

    val filteredItems = com.myapplication.common.data.CatalogData.fullCatalog.filter { 
        (selectedBrand == "All" || it.store == selectedBrand) &&
        (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo Neutro para tienda
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE5E5E5)))

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
                        ShopItemCardGlass(
                            item = item,
                            onOpenUrl = { url -> browserUrl = url }
                        )
                    }
                }
            }
        }
        
        // In-App Browser Dialog
        if (browserUrl != null) {
            Dialog(
                onDismissRequest = { browserUrl = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Browser Toolbar
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Color(0xFF1E1E24)).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { browserUrl = null }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close Browser", tint = Color.White)
                            }
                            Text("Store Checkout", color = Color.White, style = MaterialTheme.typography.subtitle1)
                        }
                        // Web Content
                        WebBrowserView(url = browserUrl!!, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

data class ShopItem(val id: String, val name: String, val store: String, val price: Double, val image: String, val affiliateLink: String)

@Composable
fun ShopItemCardGlass(item: ShopItem, onOpenUrl: (String) -> Unit) {
    GlassPanel(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { 
                // The WebViewAssetLoader expects https://appassets.androidplatform.net/assets/ for local assets
                val webViewUrl = item.image.replace("file:///android_asset/", "https://appassets.androidplatform.net/assets/")
                sendTextureTo3DEngine(webViewUrl, false)
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
                
                // Add to Wardrobe Button
                var isSaved by remember { mutableStateOf(false) }
                IconButton(
                    onClick = { 
                        isSaved = !isSaved 
                        if (isSaved) {
                            // Insert into local DB
                            com.myapplication.common.db.DatabaseRepository.insertWardrobeItem(item.store, item.name, ByteArray(0))
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Filled.Add,
                        contentDescription = "Save to Wardrobe",
                        tint = if (isSaved) MaterialTheme.colors.primary else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
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
                
                // Buy Button via Purchase Agent
                val coroutineScope = rememberCoroutineScope()
                var purchaseStatus by remember { mutableStateOf<String?>(null) }
                
                Button(
                    onClick = { 
                        coroutineScope.launch {
                            purchaseStatus = "Comprando..."
                            val response = com.myapplication.common.api.AgentHubClient.purchaseGarment(item.id)
                            purchaseStatus = "Agente: Pedido en progreso"
                            println(response)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(32.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Buy", modifier = Modifier.size(14.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(purchaseStatus ?: "Delegar compra al Agente", fontSize = 10.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}
