package com.github.braiso_22.listing.application.port.`in`.addListing

fun interface AddListing {
    suspend operator fun invoke(command: AddListingCommand): AddListingResult
}
