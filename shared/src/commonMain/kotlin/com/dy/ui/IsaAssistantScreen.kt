package com.dy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.myapplication.common.api.NanoBananaClient
import kotlinx.coroutines.launch
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IsaAssistantScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        Pair("¡Hola! Soy Isa. Deshazte de esa ropa aburrida y hablemos de verdadero estilo. ¿En qué necesitas iluminación hoy?", null as String?)
    ) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val nanoClient = remember { NanoBananaClient() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { AppNavigation(currentScreen, onNavigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Chat history
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(IsaDimens.MarginMobile),
                verticalArrangement = Arrangement.spacedBy(IsaDimens.BaseSpacing * 2)
            ) {
                items(messages.size) { index ->
                    val (text, imageUrl) = messages[index]
                    if (index == 0 || index % 2 == 0) { // Isa
                        Column {
                            IsaAvatar(message = text)
                            if (imageUrl != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                KamelImage(
                                    resource = asyncPainterResource(data = imageUrl),
                                    contentDescription = "Isa's Recommendation",
                                    modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp))
                                )
                            }
                        }
                    } else { // User
                        UserMessage(message = text)
                    }
                }
                if (isLoading) {
                    item {
                        Text("Isa está juzgando tu armario...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            // Input area
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = IsaDimens.MarginMobile, vertical = IsaDimens.BaseSpacing * 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Pregúntale a Isa...") },
                        shape = RoundedCornerShape(IsaDimens.RoundFull),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    )
                    Spacer(modifier = Modifier.width(IsaDimens.BaseSpacing))
                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank() && !isLoading) {
                                val userText = textInput
                                messages.add(Pair(userText, null))
                                textInput = ""
                                isLoading = true
                                coroutineScope.launch {
                                    val result = nanoClient.chatWithIsa(userText)
                                    messages.add(Pair(result.description, result.imageUrl))
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(IsaDimens.RoundFull))
                    ) {
                        Icon(Icons.Filled.Send, contentDescription = "Enviar", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun UserMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(IsaDimens.RoundMedium)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
