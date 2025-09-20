package com.braiso_22.terracambio.listing.application.port.`in`

sealed interface AddListingResult {
    data object Success : AddListingResult
    data object BadCadastralCode : AddListingResult
    data class BadCommand(val message: String) : AddListingResult
}