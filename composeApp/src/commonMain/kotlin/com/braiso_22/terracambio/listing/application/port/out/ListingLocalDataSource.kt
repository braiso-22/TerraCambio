package com.braiso_22.terracambio.listing.application.port.out

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId

interface ListingLocalDataSource {
    suspend fun addListing(listing: Listing): Listing
    suspend fun getListingsByOwnerId(
        ownerId: OwnerId,
    ): List<Listing>
}