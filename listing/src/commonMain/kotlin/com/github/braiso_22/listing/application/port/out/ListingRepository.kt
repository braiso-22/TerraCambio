package com.github.braiso_22.listing.application.port.out

import com.github.braiso_22.listing.domain.Listing
import com.github.braiso_22.listing.domain.vo.OwnerId

interface ListingRepository {
    suspend fun add(listing: Listing)
    suspend fun getByOwnerId(id: OwnerId): List<Listing>
    suspend fun getAll(): List<Listing>
}