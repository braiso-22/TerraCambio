package com.braiso_22.terracambio.listing.infrastructure.adapters.out.providers.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopLevelObject(
    @SerialName("control")
    val control: Control,
    @SerialName("coordenadas")
    val coordenadas: Coordenadas
) {
    fun getCoordinates(): Coord {
        return coordenadas.coord[0]
    }
}