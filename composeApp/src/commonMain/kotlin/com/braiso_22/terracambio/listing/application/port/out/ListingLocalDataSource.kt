package com.braiso_22.terracambio.listing.application.port.out

import com.github.braiso_22.listing.Listing

fun interface ListingLocalDataSource {
    suspend fun addListing(listing: Listing): Listing
}