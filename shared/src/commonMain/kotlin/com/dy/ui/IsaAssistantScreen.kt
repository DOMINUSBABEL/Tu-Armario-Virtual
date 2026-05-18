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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IsaAssistantScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        "¡Hola! Soy Isa, impulsada por Gemini 2.5 Flash. ¿En qué te puedo ayudar hoy con tu estilo?"
    ) }

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
                    if (index == 0 || index % 2 == 0) {
                        IsaAvatar(message = messages[index])
                    } else {
                        UserMessage(message = messages[index])
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
                            if (textInput.isNotBlank()) {
                                messages.add(textInput)
                                messages.add("Buscando en tu armario y en las tendencias locales para responder a: '$textInput'...")
                                textInput = ""
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
