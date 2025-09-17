package com.braiso_22.terracambio.listing.infrastructure.adapters.out.repository

import com.github.braiso_22.listing.application.port.out.ListingRepository
import com.github.braiso_22.listing.domain.Listing
import com.github.braiso_22.listing.domain.vo.OwnerId

class InMemoryListingRepository : ListingRepository {

    private val listings = mutableListOf<Listing>()

    override suspend fun add(listing: Listing) {
        listings.add(listing)
    }

    override suspend fun getByOwnerId(id: OwnerId): List<Listing> {
        return listings.filter { it.ownerId == id }
    }

    override suspend fun getAll(): List<Listing> {
        return listings.toList()
    }
}