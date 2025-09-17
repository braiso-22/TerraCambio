package com.github.braiso_22.listing.application.port.`in`.addListing

import com.github.braiso_22.listing.application.port.`in`.CommandTransaction

data class AddListingCommand(
    val ownerId: String,
    val listingName: String,
    val types: List<CommandTransaction>,
    val cadastralCode: String
)
