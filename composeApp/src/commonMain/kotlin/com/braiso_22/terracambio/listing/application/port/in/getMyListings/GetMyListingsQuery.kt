package com.braiso_22.terracambio.listing.application.port.`in`.getMyListings

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId
import kotlin.uuid.ExperimentalUuidApi


fun GetMyListings(
    userLocalDataSource: UserLocalDataSource,
    listingLocalDataSource: ListingLocalDataSource,
): GetMyListings {
    return GetMyListingsQuery(userLocalDataSource, listingLocalDataSource)
}

private class GetMyListingsQuery(
    private val userLocalDataSource: UserLocalDataSource,
    private val listingLocalDataSource: ListingLocalDataSource,
) : GetMyListings {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(): List<Listing> {
        val userId = userLocalDataSource.getUserId()
        val ownerId = OwnerId(userId.value)
        return listingLocalDataSource.getListingsByOwnerId(ownerId)
    }
}