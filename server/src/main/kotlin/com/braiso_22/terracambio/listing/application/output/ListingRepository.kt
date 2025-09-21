package com.braiso_22.terracambio.listing.application.output

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId

interface ListingRepository {
    suspend fun add(listing: Listing)
    suspend fun getByOwnerId(id: OwnerId): List<Listing>
    suspend fun getAll(): List<Listing>
}