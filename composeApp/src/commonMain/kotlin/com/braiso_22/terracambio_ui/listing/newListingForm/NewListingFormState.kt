package com.braiso_22.terracambio_ui.listing.newListingForm

import androidx.compose.runtime.Stable

@Stable
data class NewListingFormState(
    val cadastralCode: String,
    val invalidCadastralCode: Boolean,
    val isLoadingCadastralCode: Boolean,
    val isSell: Boolean,
    val sellPrice: String,
    val invalidSellPrice: Boolean,
    val isRent: Boolean,
    val invalidRentPrice: Boolean,
    val rentPrice: String,
    val isSwitch: Boolean,
)