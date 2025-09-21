package com.github.braiso_22.listing

import com.github.braiso_22.listing.vo.ListingId
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.Location
import com.github.braiso_22.listing.vo.OwnerId

data class Listing (
    val id: ListingId,
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val location: Location,
    val ownerId: OwnerId
)
