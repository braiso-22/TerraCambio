package com.braiso_22.terracambio.listing.application.input.getListings

import com.braiso_22.terracambio.listing.application.output.ListingRepository
import com.github.braiso_22.listing.Listing

fun interface GetListings {
    suspend operator fun invoke(): List<Listing>
}

fun GetListings(
    listingRepository: ListingRepository,
): GetListings = GetListingsUseCase(listingRepository)

private class GetListingsUseCase(
    private val listingRepository: ListingRepository,
) : GetListings {
    override suspend fun invoke(): List<Listing> {
        val listings = listingRepository.getAll()
        return listings
    }
}

