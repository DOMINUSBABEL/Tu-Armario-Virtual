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
data class GeminiPart(val text: String? = null, val inlineData: InlineData? = null)

@Serializable
data class InlineData(val mimeType: String, val data: String)

@Serializable
data class GeminiContent(val parts: List<GeminiPart>)

@Serializable
data class GeminiRequest(val contents: List<GeminiContent>)

@Serializable
data class GeminiResponse(val candidates: List<GeminiCandidate>? = null)

@Serializable
data class GeminiCandidate(val content: GeminiContent)

class NanoBananaClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val API_KEY = "AIzaSyBBnMT0repDOFQ1J-dVr_s7nh9vbs7XhiY"

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun getOutfitSuggestion(imageBytesList: List<ByteArray>, theme: String = "", itemDescriptions: List<String> = emptyList()): String {
        return try {
            val parts = mutableListOf<GeminiPart>()
            
            val themeText = if (theme.isNotBlank()) " para el tema/ocasión: $theme" else ""
            val itemsText = if (itemDescriptions.isNotEmpty()) " (Prendas provistas: ${itemDescriptions.joinToString(", ")})" else ""
            val promptText = "Eres un estilista experto en moda. Analiza las prendas de ropa en las imágenes proporcionadas$itemsText. Sugiere una combinación completa de outfit usando estas prendas$themeText, detallando por qué combinan bien en cuanto a color, estilo y el tema. Responde en Español de forma amigable."
            
            parts.add(GeminiPart(text = promptText))

            imageBytesList.forEach { bytes ->
                val base64Image = Base64.encode(bytes)
                parts.add(GeminiPart(inlineData = InlineData(mimeType = "image/jpeg", data = base64Image)))
            }

            val request = GeminiRequest(contents = listOf(GeminiContent(parts = parts)))

            val response: GeminiResponse = client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$API_KEY") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No se pudo generar una sugerencia."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error de conexión con Nano Banana / Gemini API: ${e.message}"
        }
    }
}
