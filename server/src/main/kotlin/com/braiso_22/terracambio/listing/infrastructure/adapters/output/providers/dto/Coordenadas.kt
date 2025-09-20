package com.braiso_22.terracambio.listing.infrastructure.adapters.output.providers.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coordenadas(
    @SerialName("coord")
    val coord: List<Coord>
)