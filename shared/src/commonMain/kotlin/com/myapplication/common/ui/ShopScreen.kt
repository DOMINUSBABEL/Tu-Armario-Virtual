package com.myapplication.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.gamification.GameState
import com.myapplication.common.ui.theme.*

@Composable
fun ShopScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Avatar & Rewards Shop", style = MaterialTheme.typography.h3) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colors.onSurface)
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Current Balance
            item {
                BalanceCard()
            }

            // Real Rewards / Unboxings
            item {
                Text("Exclusive Rewards", style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.height(16.dp))
                RewardCard(
                    brand = "Adidas",
                    discount = "15% OFF",
                    description = "Unlock a real-life discount code for your next Adidas purchase.",
                    cost = 5000,
                    isLocked = GameState.dyCoins < 5000
                )
                Spacer(modifier = Modifier.height(16.dp))
                RewardCard(
                    brand = "Zara",
                    discount = "Free Shipping + 10% OFF",
                    description = "Exclusive perk for Top Models.",
                    cost = 8000,
                    isLocked = GameState.dyCoins < 8000
                )
            }

            // Virtual Avatar Clothes
            item {
                Text("Avatar Boutique", style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.height(16.dp))
                AvatarItemCard("Golden Crown", "Legendary Headwear", 1500)
                Spacer(modifier = Modifier.height(12.dp))
                AvatarItemCard("Neon Jacket", "Epic Outerwear", 800)
                Spacer(modifier = Modifier.height(12.dp))
                AvatarItemCard("Classic White Sneakers", "Rare Shoes", 400)
            }
        }
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your Balance", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = "Coins", tint = Color(0xFFFFD700), modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("${GameState.dyCoins}", color = Color.White, style = MaterialTheme.typography.h1)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Earn more coins by joining the Runway, voting on outfits, and uploading new clothes.",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RewardCard(brand: String, discount: String, description: String, cost: Int, isLocked: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(enabled = !isLocked) { /* Buy logic */ },
        elevation = 4.dp,
        backgroundColor = if (isLocked) Color(0xFFF5F5F5) else MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isLocked) {
                    Icon(Icons.Filled.Lock, contentDescription = "Locked", tint = Color.Gray)
                } else {
                    Text(brand.first().toString(), style = MaterialTheme.typography.h2, color = Color.DarkGray)
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(brand, style = MaterialTheme.typography.overline, color = MaterialTheme.colors.secondary)
                Text(discount, style = MaterialTheme.typography.h3, color = if (isLocked) Color.Gray else MaterialTheme.colors.primary)
                Text(description, style = MaterialTheme.typography.caption, color = Color.Gray, maxLines = 2)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("$cost", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold, color = if(isLocked) Color.Gray else Color(0xFFB8860B))
                Text("Coins", style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
fun AvatarItemCard(name: String, rarity: String, cost: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* Buy logic */ },
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(name, style = MaterialTheme.typography.subtitle1, color = MaterialTheme.colors.primary)
                Text(rarity, style = MaterialTheme.typography.caption, color = MaterialTheme.colors.secondary)
            }
            
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("$cost Coins", style = MaterialTheme.typography.button, color = MaterialTheme.colors.primary)
            }
        }
    }
}
