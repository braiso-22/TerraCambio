package com.github.braiso_22.listing.vo

data class Location(
    val name: String,
    val cadastralCode: CadastralCode,
    val geoLocation: GeoLocation,
) {
    init {
        require(name.isNotBlank()) { "Location name must not be blank" }
    }
}