package com.braiso_22.terracambio.listing.presentation.newListingPanel

sealed interface NewListingUiEvent {
    data object BadName : NewListingUiEvent
    data object InvalidTransactions : NewListingUiEvent
    data object InvalidCadastralCode : NewListingUiEvent
    data object CadastralCodeNotFound : NewListingUiEvent
    data object CouldNotCreate : NewListingUiEvent
    data object ListingCreated : NewListingUiEvent
}