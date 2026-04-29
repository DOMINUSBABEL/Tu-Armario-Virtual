package com.myapplication.common.data

import com.myapplication.common.model.WardrobeItem

object MockData {
    val defaultWardrobe = listOf(
        WardrobeItem(
            id = "1",
            name = "Classic White Tee",
            category = "Tops",
            brand = "Uniqlo",
            imageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60"
        ),
        WardrobeItem(
            id = "2",
            name = "Vintage Blue Jeans",
            category = "Bottoms",
            brand = "Levi's",
            imageUrl = "https://images.unsplash.com/photo-1542272604-787c3835535d?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60"
        ),
        WardrobeItem(
            id = "3",
            name = "Green Gazelle Sneakers",
            category = "Shoes",
            brand = "Adidas",
            imageUrl = "https://images.unsplash.com/photo-1608231387042-66d1773070a5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60"
        ),
        WardrobeItem(
            id = "4",
            name = "Black Leather Jacket",
            category = "Outerwear",
            brand = "Zara",
            imageUrl = "https://images.unsplash.com/photo-1551028719-00167b16eac5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60"
        ),
        WardrobeItem(
            id = "5",
            name = "Beige Chinos",
            category = "Bottoms",
            brand = "H&M",
            imageUrl = "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60"
        )
    )
}
