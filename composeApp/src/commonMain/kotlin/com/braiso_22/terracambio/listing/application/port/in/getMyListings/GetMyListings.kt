package com.braiso_22.terracambio.listing.application.port.`in`.getMyListings

import com.github.braiso_22.listing.Listing
import kotlinx.coroutines.flow.Flow

fun interface GetMyListings {
     operator fun invoke(): Flow<List<Listing>>
}