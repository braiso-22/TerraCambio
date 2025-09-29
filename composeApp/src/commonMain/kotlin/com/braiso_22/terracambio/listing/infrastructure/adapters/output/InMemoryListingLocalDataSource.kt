package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryListingLocalDataSource(initialItems: List<Listing> = emptyList()) :
    ListingLocalDataSource {
    private val listings = MutableStateFlow(initialItems)


    override suspend fun addListing(listing: Listing): Listing {
        listings.update { old -> old + listing }
        return listing
    }

    override fun getListingsByOwnerId(ownerId: OwnerId): Flow<List<Listing>> {
        return listings.map { all -> all.filter { it.ownerId == ownerId } }
    }
}