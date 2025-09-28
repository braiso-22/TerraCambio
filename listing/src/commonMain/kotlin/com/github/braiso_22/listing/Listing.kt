package com.github.braiso_22.listing

import com.github.braiso_22.listing.vo.*

data class Listing(
    val id: ListingId,
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val location: Location,
    val ownerId: OwnerId,
) {
    companion object {
        fun example(ownerId: OwnerId) = Listing(
            ListingId.example,
            ListingName.example,
            ListingTransactions.buyExample,
            Location.example,
            ownerId
        )
    }
}
