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
    suspend fun getOutfitSuggestion(imageBytes: ByteArray, occasion: String = "", model: String = "llava"): String {
        return try {
            val base64Image = Base64.encode(imageBytes)

            val occasionText = if (occasion.isNotBlank()) " for the occasion: $occasion" else ""
            val promptText = "You are an expert fashion stylist. Analyze the clothing items in the provided image. Suggest 2 complete outfit combinations using these items$occasionText, detailing why they work well together based on color, style, and occasion. Respond in Spanish."

            val request = OllamaRequest(
                model = model,
                prompt = promptText,
                images = listOf(base64Image)
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
