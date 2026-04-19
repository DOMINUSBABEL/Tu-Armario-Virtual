package com.myapplication.common.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class OllamaRequest(
    val model: String,
    val prompt: String,
    val images: List<String> = emptyList(),
    val stream: Boolean = false
)

@Serializable
data class OllamaResponse(
    val response: String
)

class OllamaClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun getOutfitSuggestion(imageBytesList: List<ByteArray>, theme: String = "", itemDescriptions: List<String> = emptyList(), model: String = "llava"): String {
        return try {
            val base64Images = imageBytesList.map { Base64.encode(it) }

            val themeText = if (theme.isNotBlank()) " for the theme/occasion: $theme" else ""
            val itemsText = if (itemDescriptions.isNotEmpty()) " (Items provided: ${itemDescriptions.joinToString(", ")})" else ""
            val promptText = "You are an expert fashion stylist. Analyze the clothing items in the provided images$itemsText. Suggest a complete outfit combination using these items$themeText, detailing why they work well together based on color, style, and the theme. Respond in Spanish."

            val request = OllamaRequest(
                model = model,
                prompt = promptText,
                images = base64Images
            )

            val response: OllamaResponse = client.post("http://localhost:11434/api/generate") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

            response.response
        } catch (e: Exception) {
            e.printStackTrace()
            "Error de conexión con el modelo local de IA. Por favor, asegúrate de que Ollama está corriendo en http://localhost:11434 con un modelo de visión como 'llava' o 'qwen-vl'."
        }
    }
}
