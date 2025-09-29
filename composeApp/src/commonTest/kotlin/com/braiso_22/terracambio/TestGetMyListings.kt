package com.braiso_22.terracambio

import com.braiso_22.terracambio.listing.application.port.`in`.getMyListings.GetMyListings
import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.FakeUserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingLocalDataSource
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.OwnerId
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TestGetMyListings {

    private lateinit var getMyListings: GetMyListings
    private var userLocalDataSource: UserLocalDataSource = FakeUserLocalDataSource()
    private var listingLocalDataSource: ListingLocalDataSource = InMemoryListingLocalDataSource()

    @BeforeTest
    fun setup() {
        getMyListings = GetMyListings(
            userLocalDataSource,
            listingLocalDataSource
        )
    }

    @Test
    fun `Returns empty when no listings`() = runTest {
        val myListings = getMyListings().first()
        assert(myListings.isEmpty())
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Returns empty when no listings for currentUser`() = runTest {
        // Add a listing with a different ownerId
        listingLocalDataSource.addListing(
            Listing.exampleWithOwner(OwnerId(Uuid.random()))
        )

        val myListings = getMyListings().first()
        assert(myListings.isEmpty())
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Returns values when listings for currentUser`() = runTest {
        // Add a listing with a same ownerId
        val id = userLocalDataSource.getUserId()
        listingLocalDataSource.addListing(
            Listing.exampleWithOwner(OwnerId(id.value))
        )

        val myListings = getMyListings().first()
        assert(myListings.isNotEmpty())
    }
}