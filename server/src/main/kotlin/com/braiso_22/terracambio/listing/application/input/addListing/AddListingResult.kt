package com.braiso_22.terracambio.listing.application.input.addListing

sealed interface AddListingResult {
    data object Success : AddListingResult
    data class BadCommand(val message: String) : AddListingResult
    data class CadastralCodeNotFound(val message: String) : AddListingResult
}