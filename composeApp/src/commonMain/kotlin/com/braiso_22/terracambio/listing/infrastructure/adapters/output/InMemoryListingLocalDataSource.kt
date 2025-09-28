package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId

class InMemoryListingLocalDataSource : ListingLocalDataSource {
    var listings = mutableListOf<Listing>()
    override suspend fun addListing(listing: Listing): Listing {
        listings.add(listing)
        return listing
    }

    override suspend fun getListingsByOwnerId(ownerId: OwnerId): List<Listing> {
        return listings.filter { it.ownerId == ownerId }
    }
}