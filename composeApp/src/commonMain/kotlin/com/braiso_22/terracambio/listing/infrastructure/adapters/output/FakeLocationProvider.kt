package com.braiso_22.terracambio.listing.infrastructure.adapters.output

import com.braiso_22.terracambio.listing.application.port.out.LocationProvider
import com.github.braiso_22.listing.vo.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class FakeLocationProvider(val returnsNull: Boolean) : LocationProvider {
    override suspend fun getByCadastralCode(code: CadastralCode): Location? {
        delay(1.seconds)
        return if (returnsNull) {
            null
        } else {
            Location(
                name = "Frank Alvarado",
                cadastralCode = CadastralCode(value = "12341234123412"),
                geoLocation = GeoLocation(
                    latitude = Latitude(value = 14.15),
                    longitude = Longitude(value = 18.19)
                )
            )
        }

    }
}