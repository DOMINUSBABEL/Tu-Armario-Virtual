package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder

@Composable
fun ShopScreen(onNavigateBack: () -> Unit) {
    val mockShopItems = listOf(
        ShopItem("1", "Cyberpunk Jacket", "Temu", 15.99, "https://temu.com/mock"),
        ShopItem("2", "Y2K Cargo Pants", "Shein", 22.50, "https://shein.com/mock"),
        ShopItem("3", "Neon Tech Vest", "Temu", 18.00, "https://temu.com/mock"),
        ShopItem("4", "Minimalist Turtleneck", "Shein", 12.99, "https://shein.com/mock")
    )

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
                        title = { Text("Fast Fashion (3D Try-On)", style = MaterialTheme.typography.h3, color = Color.White) },
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
            ) {
                items(mockShopItems) { item ->
                    ShopItemCardGlass(item)
                }
            }
        }
    }
}

data class ShopItem(val id: String, val name: String, val store: String, val price: Double, val affiliateLink: String)

@Composable
fun ShopItemCardGlass(item: ShopItem) {
    GlassPanel(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { /* Tapping applies texture to 3D avatar */ },
        alpha = 0.7f,
        cornerRadius = 16.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Image Placeholder (Transparent/Slight tint)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tap to Try-On 3D",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption
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
