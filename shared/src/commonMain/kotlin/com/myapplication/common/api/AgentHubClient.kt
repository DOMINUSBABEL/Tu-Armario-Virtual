package com.myapplication.common.api

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object AgentHubClient {
    private val nanoClient = NanoBananaClient()

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun identifyGarment(imageBase64: String): String {
        return try {
            // Standalone app mode: call NanoBanana/Gemini directly
            val bytes = Base64.decode(imageBase64.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", ""))
            nanoClient.getOutfitSuggestion(listOf(bytes), "Identificación de prenda", emptyList())
        } catch (e: Exception) {
            "Error delegating to NanoBanana API: ${e.message}"
        }
    }

    suspend fun matchStore(tags: List<String>): String {
        // Simulated local execution for standalone mode
        return "El Agente (Modo Autónomo) ha buscado en la web y encontró 3 coincidencias aproximadas para: ${tags.joinToString()}."
    }

    suspend fun purchaseGarment(itemId: String): String {
        // Simulated local execution for standalone mode
        return "Orden local delegada exitosamente para el item: $itemId"
    }
}

