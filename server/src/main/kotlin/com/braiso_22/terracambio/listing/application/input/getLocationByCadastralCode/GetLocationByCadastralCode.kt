package com.braiso_22.terracambio.listing.application.input.getLocationByCadastralCode

import com.braiso_22.terracambio.listing.application.output.LocationProvider

fun interface GetLocationByCadastralCode {
    suspend operator fun invoke(command: GetLocationByCadastralCodeQuery): GetLocationByCadastralCodeResult
}

fun GetLocationByCadastralCode(locationProvider: LocationProvider): GetLocationByCadastralCode {
    return GetLocationByCadastralCodeUseCase(locationProvider)
}

private class GetLocationByCadastralCodeUseCase(private val locationProvider: LocationProvider) :
    GetLocationByCadastralCode {
    override suspend fun invoke(command: GetLocationByCadastralCodeQuery): GetLocationByCadastralCodeResult {

        val location = locationProvider.getByCadastralCode(
            command.code
        ) ?: return GetLocationByCadastralCodeResult.NotFound

        return GetLocationByCadastralCodeResult.Valid(location)
    }
}