package com.dy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemCard(
    title: String,
    brand: String,
    price: String,
    rating: Int,
    onTryClick: () -> Unit,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(IsaDimens.BaseSpacing)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(IsaDimens.RoundMedium),
                spotColor = Color(0x26D6C6E1) // Soft shadow based on lavender #d6c6e1
            ),
        shape = RoundedCornerShape(IsaDimens.RoundMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column {
            // Image Placeholder (Lavender / Secondary)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Image Placeholder", 
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = brand.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(IsaDimens.BaseSpacing))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(IsaDimens.BaseSpacing))
                
                // Minimalist Stars
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = if (index < rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(IsaDimens.Gutter))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = price,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Row {
                        OutlinedButton(
                            onClick = onTryClick,
                            shape = RoundedCornerShape(IsaDimens.RoundMedium)
                        ) {
                            Text("Probar (VTO)")
                        }
                        Spacer(modifier = Modifier.width(IsaDimens.BaseSpacing * 2))
                        Button(
                            onClick = onBuyClick,
                            shape = RoundedCornerShape(IsaDimens.RoundMedium),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Comprar")
                        }
                    }
                }
            }
        }
    }
}
