package com.braiso_22.terracambio.listing.presentation.mapListings

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class MapListingItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
)
