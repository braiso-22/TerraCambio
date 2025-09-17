package com.braiso_22.terracambio.listing.infrastructure.adapters.out.repository

import com.github.braiso_22.listing.application.port.out.ListingRepository
import com.github.braiso_22.listing.domain.Listing
import com.github.braiso_22.listing.domain.vo.OwnerId

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