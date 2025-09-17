package com.braiso_22.terracambio.listing.application

import com.github.braiso_22.listing.application.port.`in`.validateCadastralCode.ValidateCadastralCode
import com.github.braiso_22.listing.application.port.out.LocationProvider
import com.github.braiso_22.listing.domain.vo.CadastralCode
import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeCommand
import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeResult

fun ValidateCadastralCode(locationProvider: LocationProvider): ValidateCadastralCode {
    return ValidateCadastralCodeUseCase(locationProvider)
}

private class ValidateCadastralCodeUseCase(private val locationProvider: LocationProvider) : ValidateCadastralCode {
    override suspend fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult {
        val code = try {
            CadastralCode(command.code)
        } catch (ex: IllegalArgumentException) {
            return ValidateCadastralCodeResult.InvalidFormat(ex.message ?: "Invalid format")
        }
        locationProvider.getByCadastralCode(
            code
        ) ?: return ValidateCadastralCodeResult.NotFound

        return ValidateCadastralCodeResult.Valid
    }
}