package com.github.braiso_22.listing.vo

data class Location(
    val name: String,
    val cadastralCode: CadastralCode,
    val geoLocation: GeoLocation,
) {
    init {
        require(name.isNotBlank()) { "Location name must not be blank" }
    }

    companion object {
        val example = Location(
            name = "Wilma Trevino",
            cadastralCode = CadastralCode.example,
            geoLocation = GeoLocation(
                latitude = Latitude(value = 22.23),
                longitude = Longitude(value = 26.27)
            )
        )
    }
}