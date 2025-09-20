package com.braiso_22.terracambio.listing.application.input.addListing

import com.braiso_22.terracambio.listing.application.output.ListingRepository
import com.braiso_22.terracambio.listing.application.output.LocationProvider
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.ListingId
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun interface AddListing {
    suspend operator fun invoke(command: AddListingCommand): AddListingResult
}


fun AddListing(
    listingRepository: ListingRepository,
    locationProvider: LocationProvider,
): AddListing {
    return AddListingUseCase(
        listingRepository,
        locationProvider
    )
}

private class AddListingUseCase(
    private val listingRepository: ListingRepository,
    private val locationProvider: LocationProvider,
) : AddListing {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(command: AddListingCommand): AddListingResult {


        val location = locationProvider.getByCadastralCode(command.cadastralCode)
            ?: return AddListingResult.CadastralCodeNotFound("Cadastral code not found")

        val listing = try {
            Listing(
                id = ListingId(Uuid.random()),
                name = command.listingName,
                listingTransactions = command.transactions,
                location = location,
                ownerId = command.ownerId
            )
        } catch (e: IllegalArgumentException) {
            return AddListingResult.BadCommand(e.message ?: "Invalid command parameters")
        }

        listingRepository.add(listing)

        return AddListingResult.Success
    }
}