package com.braiso_22.terracambio.listing.newListingForm

sealed interface NewListingUserInteractions {
    data class OnChangeCadastralCode(val newName: String) : NewListingUserInteractions
    data object OnCheckSell : NewListingUserInteractions
    data class OnChangeSellPrice(val newPrice: String) : NewListingUserInteractions
    data object OnCheckRent : NewListingUserInteractions
    data class OnChangeRentPrice(val newPrice: String) : NewListingUserInteractions
    data object OnCheckSwitch : NewListingUserInteractions
}