package com.braiso_22.terracambio.listing.infrastructure.adapters.out.providers

import com.github.braiso_22.listing.application.port.out.LocationProvider
import com.github.braiso_22.listing.domain.vo.CadastralCode
import com.github.braiso_22.listing.domain.vo.GeoLocation
import com.github.braiso_22.listing.domain.vo.Latitude
import com.github.braiso_22.listing.domain.vo.Location
import com.github.braiso_22.listing.domain.vo.Longitude
import kotlin.random.Random

class FakeLocationProvider(
    private val activated: Boolean = true
) : LocationProvider {
    override suspend fun getByCadastralCode(code: CadastralCode): Location? {
        return if (!activated) {
            null
        } else {
            val randomGenerator = Random(code.value.hashCode())

            Location(
                name = "Fake location-$code",
                cadastralCode = code,
                geoLocation = GeoLocation(
                    latitude = Latitude(
                        value = randomGenerator.nextDouble(-90.0, 90.0)
                    ),
                    longitude = Longitude(
                        value = randomGenerator.nextDouble(-180.0, 180.0)
                    )
                )
            )
        }
    }
}