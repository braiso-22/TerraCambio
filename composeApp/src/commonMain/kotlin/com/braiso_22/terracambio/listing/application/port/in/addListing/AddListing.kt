package com.braiso_22.terracambio.listing.application.port.`in`.addListing

fun interface AddListing {
    suspend operator fun invoke(command: AddListingCommand): AddListingResult
}