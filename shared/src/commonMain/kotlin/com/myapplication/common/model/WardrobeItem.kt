package com.myapplication.common.model

data class WardrobeItem(
    val id: String,
    val name: String,
    val category: String, // e.g., "Tops", "Bottoms", "Shoes", "Outerwear"
    val brand: String,
    val imageUrl: String // Placeholder URL
)
