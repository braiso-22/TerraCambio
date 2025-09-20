package com.braiso_22.terracambio.listing.infrastructure.adapters.out

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.github.braiso_22.listing.domain.Listing

class InMemoryListingLocalDataSource : ListingLocalDataSource {
    override suspend fun addListing(listing: Listing): Listing {
        TODO()
    }
}