package com.braiso_22.terracambio.listing.infrastructure.adapters.output.providers

import com.braiso_22.terracambio.listing.application.output.LocationProvider
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.Location
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.providers.dto.CadastralResponseDto

const val baseUrl = "http://ovc.catastro.meh.es/OVCServWeb/OVCWcfCallejero/COVCCoordenadas.svc/json/Consulta_CPMRC"

class KtorHttpClientLocationProvider(private val httpClient: HttpClient) : LocationProvider {

    private suspend fun getLocationByCode(code: String): CadastralResponseDto {
        val params = "?RefCat=${code}&SRS=EPSG:4326"
        return httpClient.get(baseUrl + params).body<CadastralResponseDto>()
    }

    override suspend fun getByCadastralCode(code: CadastralCode): Location? {
        return try {
            val cadastralResponse = getLocationByCode(code.value)

            cadastralResponse.toDomain(code)
        } catch (_: Exception) {
            null
        }
    }
}