package com.braiso_22.terracambio.listing.infrastructure.adapters.out

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) { json() }
}