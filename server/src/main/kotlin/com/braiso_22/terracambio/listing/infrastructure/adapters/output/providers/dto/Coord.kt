package com.braiso_22.terracambio.listing.infrastructure.adapters.output.providers.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    @SerialName("geo")
    val geo: Geo,
    @SerialName("ldt")
    val fullName: String,
    @SerialName("pc")
    val pc: Pc
) {

    fun getLatitude(): Double {
        return geo.ycen.toDouble()
    }

    fun getLongitude(): Double {
        return geo.xcen.toDouble()
    }
}