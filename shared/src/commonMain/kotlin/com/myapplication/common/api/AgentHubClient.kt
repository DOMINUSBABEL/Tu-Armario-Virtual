package com.myapplication.common.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

object AgentHubClient {
    private const val HUB_URL = "http://10.0.2.2:3001/api/hub" // Node.js Orchestrator

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun identifyGarment(imageBase64: String): String {
        return try {
            val response: String = client.post("$HUB_URL/identify") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("image" to imageBase64))
            }.body()
            response
        } catch (e: Exception) {
            "Error delegating to Agent Hub: ${e.message}"
        }
    }

    suspend fun matchStore(tags: List<String>): String {
        return try {
            val response: String = client.post("$HUB_URL/match") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("tags" to tags))
            }.body()
            response
        } catch (e: Exception) {
            "Error matching store: ${e.message}"
        }
    }

    suspend fun purchaseGarment(itemId: String): String {
        return try {
            val response: String = client.post("$HUB_URL/purchase") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("itemId" to itemId))
            }.body()
            response
        } catch (e: Exception) {
            "Error purchasing garment: ${e.message}"
        }
    }
}
