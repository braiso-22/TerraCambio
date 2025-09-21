package com.braiso_22.terracambio.listing.infrastructure.adapters.output.repository

import com.braiso_22.terracambio.listing.application.output.ListingRepository
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId

class ExposedListingRepository : ListingRepository {
    override suspend fun add(listing: Listing) {
        TODO("Not yet implemented")
    }

    override suspend fun getByOwnerId(id: OwnerId): List<Listing> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Listing> {
        TODO("Not yet implemented")
    }
}