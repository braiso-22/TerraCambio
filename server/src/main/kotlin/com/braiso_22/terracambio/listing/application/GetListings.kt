package com.braiso_22.terracambio.listing.application

import com.github.braiso_22.listing.application.port.`in`.getListings.GetListings
import com.github.braiso_22.listing.application.port.out.ListingRepository
import com.github.braiso_22.listing.domain.Listing


fun GetListings(
    listingRepository: ListingRepository
): GetListings = GetListingsUseCase(listingRepository)

private class GetListingsUseCase(
    private val listingRepository: ListingRepository
) : GetListings {

    override suspend fun invoke(): List<Listing> {
        val listings = listingRepository.getAll()
        return listings
    }
}
