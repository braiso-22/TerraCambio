package com.braiso_22.terracambio.listing.application.port.`in`.getMyListings

import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
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
    override fun invoke(): Flow<List<Listing>> = flow {
        val userId = userLocalDataSource.getUserId() // suspend call
        val ownerId = OwnerId(userId.value)
        emitAll(listingLocalDataSource.getListingsByOwnerId(ownerId))
    }
}