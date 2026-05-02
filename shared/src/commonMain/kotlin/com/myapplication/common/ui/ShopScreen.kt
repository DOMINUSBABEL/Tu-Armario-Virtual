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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.ui.components.GlassPanel
import com.myapplication.common.ui.components.UnityViewPlaceholder

@Composable
fun ShopScreen(onNavigateBack: () -> Unit) {
    val mockShopItems = listOf(
        // Vélez
        ShopItem("1", "Chaqueta De Cuero Mujer Biker", "Vélez", 999900.0, "https://www.velez.com.co/lo-nuevo/mujer"),
        ShopItem("2", "Bolso Manos Libres De Cuero", "Vélez", 359900.0, "https://www.velez.com.co/lo-nuevo/mujer"),
        ShopItem("3", "Botines De Cuero Tacón", "Vélez", 459900.0, "https://www.velez.com.co/lo-nuevo/mujer"),
        ShopItem("4", "Cinturón De Cuero Grabado", "Vélez", 149900.0, "https://www.velez.com.co/lo-nuevo/mujer"),
        
        // Arturo Calle
        ShopItem("5", "Blusa Cuello V Manga Sisa", "Arturo Calle", 89900.0, "https://www.arturocalle.com/"),
        ShopItem("6", "Pantalón Dril Fondo Entero", "Arturo Calle", 129900.0, "https://www.arturocalle.com/"),
        ShopItem("7", "Chaqueta En Denim Básica", "Arturo Calle", 189900.0, "https://www.arturocalle.com/"),
        ShopItem("8", "Vestido Largo Estampado", "Arturo Calle", 159900.0, "https://www.arturocalle.com/"),
        
        // True
        ShopItem("9", "Camiseta Oversize True Logo", "TRUE", 85000.0, "https://trueshop.co/"),
        ShopItem("10", "Pantalón Parachute Cargo", "TRUE", 210000.0, "https://trueshop.co/"),
        ShopItem("11", "Hoodie Básica True", "TRUE", 175000.0, "https://trueshop.co/"),
        ShopItem("12", "Falda Midi Cargo", "TRUE", 145000.0, "https://trueshop.co/")
    )

    var selectedBrand by remember { mutableStateOf("All") }
    val brands = listOf("All", "Vélez", "Arturo Calle", "TRUE")

    val filteredItems = if (selectedBrand == "All") {
        mockShopItems
    } else {
        mockShopItems.filter { it.store == selectedBrand }
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
