package com.braiso_22.listing.domain

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.*
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ListingDomainValidationTest {
    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `listing requires non-blank name and at least one type`() {
        val id = ListingId.example()
        val name = ListingName("Nice lising")
        val owner = OwnerId(Uuid.random())
        val loc = Location.example
        // valid
        Listing(
            id = id,
            name = name,
            listingTransactions = ListingTransactions(setOf(TransactionType.Switch)),
            location = loc,
            ownerId = owner
        )

        // blank name
        assertFailsWith<IllegalArgumentException> {
            Listing(
                id = id,
                name = ListingName("\t\n  "),
                listingTransactions = ListingTransactions(setOf(TransactionType.Switch)),
                location = loc,
                ownerId = owner
            )
        }
        // no types
        assertFailsWith<IllegalArgumentException> {
            Listing(
                id = id,
                name = name,
                listingTransactions = ListingTransactions(emptySet()),
                location = loc,
                ownerId = owner
            )
        }
    }
}
