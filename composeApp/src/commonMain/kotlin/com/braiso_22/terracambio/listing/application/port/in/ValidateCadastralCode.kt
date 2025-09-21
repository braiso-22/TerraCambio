package com.braiso_22.terracambio.listing.application.port.`in`

import com.braiso_22.terracambio.listing.application.port.out.LocationProvider
import com.github.braiso_22.listing.vo.CadastralCode

fun interface ValidateCadastralCode {
    suspend operator fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult
}

fun ValidateCadastralCode(locationProvider: LocationProvider): ValidateCadastralCode {
    return ValidateCadastralCodeUseCase(locationProvider)
}

private class ValidateCadastralCodeUseCase(private val locationProvider: LocationProvider) :
    ValidateCadastralCode {
    override suspend fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult {

        locationProvider.getByCadastralCode(
            command.code
        ) ?: return ValidateCadastralCodeResult.NotFound

        return ValidateCadastralCodeResult.Valid
    }
}

data class ValidateCadastralCodeCommand(
    val code: CadastralCode,
)

sealed interface ValidateCadastralCodeResult {
    data object Valid : ValidateCadastralCodeResult
    data object NotFound : ValidateCadastralCodeResult
}