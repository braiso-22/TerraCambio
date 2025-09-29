package com.braiso_22.terracambio.listing.application.port.out

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId
import kotlinx.coroutines.flow.Flow

interface ListingLocalDataSource {
    suspend fun addListing(listing: Listing): Listing
    fun getListingsByOwnerId(
        ownerId: OwnerId,
    ): Flow<List<Listing>>
}