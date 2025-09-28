package com.github.braiso_22.listing

import com.github.braiso_22.listing.vo.*
import kotlin.uuid.ExperimentalUuidApi

data class Listing(
    val id: ListingId,
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val location: Location,
    val ownerId: OwnerId,
) {
    companion object {
        val example = Listing(
            ListingId.example,
            ListingName.example,
            ListingTransactions.buyExample,
            Location.example,
            OwnerId.example
        )

        fun exampleWithOwner(ownerId: OwnerId) = Listing(
            ListingId.example,
            ListingName.example,
            ListingTransactions.buyExample,
            Location.example,
            ownerId
        )

        @OptIn(ExperimentalUuidApi::class)
        fun exampleWithIdAndOwner(id: ListingId, ownerId: OwnerId) = Listing(
            id,
            ListingName.example,
            ListingTransactions.buyExample,
            Location.example,
            ownerId
        )
    }
}
