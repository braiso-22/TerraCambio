package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.ListingServerDataSource
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingResponse
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingServerDto
import com.github.braiso_22.listing.Listing

class InMemoryListingServerDataSource : ListingServerDataSource {
    var listings = mutableListOf<Listing>()
    override suspend fun addListing(listing: AddListingServerDto): AddListingResponse {
        TODO()
    }
}