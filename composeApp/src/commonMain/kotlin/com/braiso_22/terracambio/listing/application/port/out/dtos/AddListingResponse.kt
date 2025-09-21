package com.braiso_22.terracambio.listing.application.port.out.dtos

import com.github.braiso_22.listing.Listing

sealed interface AddListingResponse {
    data class Success(val listing: Listing) : AddListingResponse
    data object CadastralCodeNotFound : AddListingResponse
    data class BadCommand(val message: String) : AddListingResponse
}