package com.braiso_22.terracambio.listing.application.input.getLocationByCadastralCode

import com.github.braiso_22.listing.vo.Location

sealed interface GetLocationByCadastralCodeResult {
    data class Valid(val location: Location) : GetLocationByCadastralCodeResult
    data object NotFound : GetLocationByCadastralCodeResult
}