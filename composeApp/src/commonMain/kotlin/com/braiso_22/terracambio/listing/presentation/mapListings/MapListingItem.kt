package com.braiso_22.terracambio.listing.presentation.mapListings

import com.github.braiso_22.listing.Listing
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class MapListingItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
) {
    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(listing: Listing): MapListingItem {
            return MapListingItem(
                id = listing.id.value,
                title = listing.name.value,
                address = listing.location.name,
                latitude = listing.location.geoLocation.latitude.value,
                longitude = listing.location.geoLocation.longitude.value,
            )
        }
    }
}
