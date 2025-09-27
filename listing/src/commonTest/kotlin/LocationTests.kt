package com.braiso_22.listing.domain

import com.github.braiso_22.listing.vo.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class LocationTests {
    @Test
    fun `latitude accepts bounds and middle`() {
        Latitude(-90.0)
        Latitude(0.0)
        Latitude(90.0)
    }

    @Test
    fun `latitude rejects out of range and non-finite`() {
        assertFailsWith<IllegalArgumentException> { Latitude(Double.NaN) }
        assertFailsWith<IllegalArgumentException> { Latitude(Double.POSITIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { Latitude(Double.NEGATIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { Latitude(-90.0000001) }
        assertFailsWith<IllegalArgumentException> { Latitude(90.0000001) }
    }

    @Test
    fun `longitude accepts bounds and middle`() {
        Longitude(-180.0)
        Longitude(0.0)
        Longitude(180.0)
    }

    @Test
    fun `longitude rejects out of range and non-finite`() {
        assertFailsWith<IllegalArgumentException> { Longitude(Double.NaN) }
        assertFailsWith<IllegalArgumentException> { Longitude(Double.POSITIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { Longitude(Double.NEGATIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { Longitude(-180.0000001) }
        assertFailsWith<IllegalArgumentException> { Longitude(180.0000001) }
    }

    @Test
    fun `geolocation accepts valid lat lon`() {
        GeoLocation(Latitude(12.34), Longitude(56.78))
    }

    @Test
    fun `location requires non-blank name`() {
        Location(
            name = "A place",
            geoLocation = GeoLocation(Latitude(0.0), Longitude(0.0)),
            cadastralCode = CadastralCode("15009A00200071")
        )
        assertFailsWith<IllegalArgumentException> {
            Location(
                geoLocation = GeoLocation(Latitude(0.0), Longitude(0.0)),
                name = " ",
                cadastralCode = CadastralCode("15009A00200071")
            )
        }
    }

    @Test
    fun `location valid cadastral code`() {
        Location(
            name = "A place",
            geoLocation = GeoLocation(Latitude(0.0), Longitude(0.0)),
            cadastralCode = CadastralCode("15009A00200071")
        )
        assertFailsWith<IllegalArgumentException> {
            Location(
                geoLocation = GeoLocation(Latitude(0.0), Longitude(0.0)),
                name = " ",
                cadastralCode = CadastralCode("")
            )
        }
    }

    @Test
    fun `Cadastral code accepts 14, 18 and 20 length values`() {
        CadastralCode("12345123451234") // 14
        CadastralCode("123451234512345678") // 18
        CadastralCode("12345123451234512345") // 20
    }
}