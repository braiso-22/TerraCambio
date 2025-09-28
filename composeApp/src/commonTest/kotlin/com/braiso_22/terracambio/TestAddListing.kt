package com.braiso_22.terracambio

import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListing
import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListingCommand
import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListingResult
import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.ListingServerDataSource
import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.FakeUserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingServerDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.ListingServerResult
import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi

class TestAddListing {

    private lateinit var addListing: AddListing
    private val listingLocalDataSource: ListingLocalDataSource = InMemoryListingLocalDataSource()
    private var listingServerDataSource: ListingServerDataSource = InMemoryListingServerDataSource(
        ListingServerResult.SUCCESS
    )
    private val userLocalDataSource: UserLocalDataSource = FakeUserLocalDataSource()

    @BeforeTest
    fun setup() {
        addListing = AddListing(
            listingLocalDataSource = listingLocalDataSource,
            listingServerDataSource = listingServerDataSource,
            userLocalDataSource = userLocalDataSource
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `when valid listing the result is valid`() = runTest {

        val cosa = addListing(
            AddListingCommand(
                listingName = ListingName.example,
                transactions = ListingTransactions.buyExample,
                cadastralCode = CadastralCode.example
            )
        )
        assert(cosa is AddListingResult.Success)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `when invalid in server the result is bad command`() = runTest {

        // Change the server to return bad command
        listingServerDataSource = InMemoryListingServerDataSource(ListingServerResult.BAD_COMMAND)
        addListing = AddListing(
            listingLocalDataSource = listingLocalDataSource,
            listingServerDataSource = listingServerDataSource,
            userLocalDataSource = userLocalDataSource
        )
        // this is a valid listing but the server will return bad command
        val cosa = addListing(
            AddListingCommand(
                listingName = ListingName.example,
                transactions = ListingTransactions.buyExample,
                cadastralCode = CadastralCode.example
            )
        )

        assert(cosa is AddListingResult.BadCommand)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `when cadastralCode not found in server the result is bad cadastral code`() = runTest {

        // Change the server to return bad command
        listingServerDataSource = InMemoryListingServerDataSource(ListingServerResult.FAILURE)
        addListing = AddListing(
            listingLocalDataSource = listingLocalDataSource,
            listingServerDataSource = listingServerDataSource,
            userLocalDataSource = userLocalDataSource
        )
        val cosa = addListing(
            AddListingCommand(
                listingName = ListingName.example,
                transactions = ListingTransactions.buyExample,
                // supposedly not found
                cadastralCode = CadastralCode.example
            )
        )

        assert(cosa is AddListingResult.BadCadastralCode)
    }
}