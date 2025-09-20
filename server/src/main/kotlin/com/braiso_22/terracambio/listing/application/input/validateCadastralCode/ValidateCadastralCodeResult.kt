package com.braiso_22.terracambio.listing.application.input.validateCadastralCode

sealed interface ValidateCadastralCodeResult {
    data object Valid : ValidateCadastralCodeResult
    data object NotFound : ValidateCadastralCodeResult
}