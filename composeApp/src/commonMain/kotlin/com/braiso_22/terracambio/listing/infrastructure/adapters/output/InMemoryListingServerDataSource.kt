package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.ListingServerDataSource
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingResponse
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingServerDto
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.ListingId
import com.github.braiso_22.listing.vo.Location
import kotlin.uuid.ExperimentalUuidApi

class InMemoryListingServerDataSource : ListingServerDataSource {
    var listings = mutableListOf<AddListingServerDto>()

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addListing(listing: AddListingServerDto): AddListingResponse {
        listings.add(listing)
        return AddListingResponse.Success(
            listing = Listing(
                id = ListingId.example,
                name = listing.name,
                listingTransactions = listing.listingTransactions,
                location = Location.example,
                ownerId = listing.ownerId
            )
        )
    }
}