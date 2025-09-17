package com.github.braiso_22.listing.application.port.`in`.addListing

sealed interface AddListingResult {
    data object Success : AddListingResult
    data class BadCommand(val message: String) : AddListingResult
    data class CadastralCodeNotFound(val message: String) : AddListingResult
}