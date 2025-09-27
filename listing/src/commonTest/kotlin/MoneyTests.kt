package com.braiso_22.listing.domain

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MoneyTests {

    @Test
    fun `money allows only positive values`() {
        // positive
        Money(1500)
    }

    @Test
    fun `money rejects negative values`() {
        assertFailsWith<IllegalArgumentException> { Money(-1) }
    }

    @Test
    fun `money rejects 0`() {
        assertFailsWith<IllegalArgumentException> { Money(0) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `listing buy and rent accept money bigger than 0`() {
        val id = ListingId(Uuid.random())
        val name = ListingName("With price")
        val owner = OwnerId(Uuid.random())
        val loc = Location(
            geoLocation = GeoLocation(Latitude(0.0), Longitude(0.0)),
            name = "Earth",
            cadastralCode = CadastralCode("15009A00200071"),
        )
        val buy = TransactionType.Buy(Money(1999))
        val rent = TransactionType.Rent(Money(1))
        val l = Listing(
            id = id,
            name = name,
            listingTransactions = ListingTransactions(setOf(buy, rent)),
            location = loc,
            ownerId = owner
        )
        assertEquals(2, l.listingTransactions.values.size)
    }

    @Test
    fun `money toDecimal converts cents to decimal correctly`() {
        assertEquals(0.01, Money(1).toDecimal(), 1e-9)
        assertEquals(19.99, Money(1999).toDecimal(), 1e-9)
        assertEquals(2.0, Money(200).toDecimal(), 1e-9)
    }

    @Test
    fun `money toDecimal handles larger values`() {
        assertEquals(1234567.89, Money(123456789).toDecimal(), 1e-9)
    }
}