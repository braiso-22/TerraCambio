package com.braiso_22.terracambio.listing.application.port.out

import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingResponse
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingServerDto

fun interface ListingServerDataSource {
    suspend fun addListing(listing: AddListingServerDto): AddListingResponse
}