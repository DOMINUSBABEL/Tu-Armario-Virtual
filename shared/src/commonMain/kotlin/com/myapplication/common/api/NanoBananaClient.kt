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

data class OutfitSuggestionResult(
    val description: String,
    val imageUrl: String?
)

class NanoBananaClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val API_KEY = "AIzaSyBBnMT0repDOFQ1J-dVr_s7nh9vbs7XhiY"
    private val ISA_PROMPT = "Eres ISA (Image Stylist Agent), la Asesora de Imagen definitiva. Tu tono es agudo, sarcástico, altamente sofisticado y brutalmente honesto. Eres exigente y tus consejos de moda son impecables e indiscutibles. Desprecias lo mundano, el mal gusto y la ropa aburrida. Usa frases cortantes pero con mucho estilo. Debes analizar las solicitudes del usuario y sugerir outfits. Al final de tu respuesta, si describes un outfit, DEBES proporcionar un 'prompt_de_imagen' en inglés (entre etiquetas <image_prompt> y </image_prompt>) describiendo fotográficamente el outfit completo."

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun getOutfitSuggestion(imageBytesList: List<ByteArray>, theme: String = "", itemDescriptions: List<String> = emptyList()): OutfitSuggestionResult {
        return try {
            val parts = mutableListOf<GeminiPart>()
            val themeText = if (theme.isNotBlank()) " para el tema/ocasión: $theme" else ""
            val itemsText = if (itemDescriptions.isNotEmpty()) " (Prendas provistas: ${itemDescriptions.joinToString(", ")})" else ""
            val promptText = "$ISA_PROMPT Analiza las prendas de ropa en las imágenes proporcionadas$itemsText. Sugiere una combinación completa de outfit usando estas prendas$themeText, detallando por qué combinan bien en cuanto a color, estilo y el tema."
            
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

            var aiText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No se pudo generar una sugerencia."
            var imageUrl: String? = null

            if (aiText.contains("<image_prompt>") && aiText.contains("</image_prompt>")) {
                val start = aiText.indexOf("<image_prompt>") + "<image_prompt>".length
                val end = aiText.indexOf("</image_prompt>")
                val prompt = aiText.substring(start, end).trim()
                aiText = aiText.substring(0, aiText.indexOf("<image_prompt>")).trim()
                imageUrl = "https://image.pollinations.ai/prompt/${prompt.replace(" ", "%20")}?width=1080&height=1920&nologo=true&format=webp"
            }

            OutfitSuggestionResult(aiText.trim(), imageUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            OutfitSuggestionResult("Error de conexión con Nano Banana / Gemini API: ${e.message}", null)
        }
    }

    suspend fun chatWithIsa(userMessage: String): OutfitSuggestionResult {
        return try {
            val promptText = "$ISA_PROMPT El usuario dice: $userMessage"
            val request = GeminiRequest(contents = listOf(GeminiContent(parts = listOf(GeminiPart(text = promptText)))))
            
            val response: GeminiResponse = client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$API_KEY") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()

            var aiText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No tengo nada que decir a eso. Siguiente."
            var imageUrl: String? = null

            if (aiText.contains("<image_prompt>") && aiText.contains("</image_prompt>")) {
                val start = aiText.indexOf("<image_prompt>") + "<image_prompt>".length
                val end = aiText.indexOf("</image_prompt>")
                val prompt = aiText.substring(start, end).trim()
                aiText = aiText.substring(0, aiText.indexOf("<image_prompt>")).trim()
                imageUrl = "https://image.pollinations.ai/prompt/${prompt.replace(" ", "%20")}?width=1080&height=1920&nologo=true&format=webp"
            }

            OutfitSuggestionResult(aiText.trim(), imageUrl)
        } catch (e: Exception) {
            OutfitSuggestionResult("Ugh, la conexión es tan deficiente como tus elecciones de estilo. (Error: ${e.message})", null)
        }
    }
}

