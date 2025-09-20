package com.braiso_22.terracambio.listing.application.port.`in`

fun interface AddListing {
    suspend operator fun invoke(command: AddListingCommand): AddListingResult
}