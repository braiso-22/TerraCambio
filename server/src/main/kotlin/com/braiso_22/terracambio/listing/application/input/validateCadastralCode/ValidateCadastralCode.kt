package com.braiso_22.terracambio.listing.application.input.validateCadastralCode

import com.braiso_22.terracambio.listing.application.output.LocationProvider

fun interface ValidateCadastralCode {
    suspend operator fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult
}

fun ValidateCadastralCode(locationProvider: LocationProvider): ValidateCadastralCode {
    return ValidateCadastralCodeUseCase(locationProvider)
}

private class ValidateCadastralCodeUseCase(private val locationProvider: LocationProvider) : ValidateCadastralCode {
    override suspend fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult {

        locationProvider.getByCadastralCode(
            command.code
        ) ?: return ValidateCadastralCodeResult.NotFound

        return ValidateCadastralCodeResult.Valid
    }
}