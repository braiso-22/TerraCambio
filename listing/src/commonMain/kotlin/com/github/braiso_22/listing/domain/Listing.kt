package com.github.braiso_22.listing.domain

import com.github.braiso_22.listing.domain.vo.ListingId
import com.github.braiso_22.listing.domain.vo.ListingName
import com.github.braiso_22.listing.domain.vo.ListingTransactions
import com.github.braiso_22.listing.domain.vo.Location
import com.github.braiso_22.listing.domain.vo.OwnerId

data class Listing (
    val id: ListingId,
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val location: Location,
    val ownerId: OwnerId
)
