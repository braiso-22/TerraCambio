package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.github.braiso_22.listing.Listing

class InMemoryListingLocalDataSource : ListingLocalDataSource {
    override suspend fun addListing(listing: Listing): Listing {
        TODO()
    }
}