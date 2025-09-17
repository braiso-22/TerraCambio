package com.braiso_22.terracambio.listing.application

import com.github.braiso_22.listing.application.port.`in`.addListing.AddListing
import com.github.braiso_22.listing.application.port.out.ListingRepository
import com.github.braiso_22.listing.application.port.out.LocationProvider
import com.github.braiso_22.listing.domain.Listing
import com.github.braiso_22.listing.domain.vo.CadastralCode
import com.github.braiso_22.listing.domain.vo.ListingId
import com.github.braiso_22.listing.domain.vo.ListingName
import com.github.braiso_22.listing.domain.vo.ListingTransactions
import com.github.braiso_22.listing.domain.vo.OwnerId
import com.github.braiso_22.listing.application.port.`in`.addListing.AddListingCommand
import com.github.braiso_22.listing.application.port.`in`.addListing.AddListingResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

        val cadastralCode = try {
            CadastralCode(command.cadastralCode)
        } catch (e: IllegalArgumentException) {
            return AddListingResult.BadCommand(e.message ?: "Invalid cadastral code")
        }

        val location = locationProvider.getByCadastralCode(cadastralCode)
            ?: return AddListingResult.CadastralCodeNotFound("Cadastral code not found")

        val listing = try {
            Listing(
                id = ListingId(Uuid.random()),
                name = ListingName(command.listingName),
                listingTransactions = ListingTransactions(command.types.map { it.toListingType() }
                    .toSet()),
                location = location,
                ownerId = OwnerId(Uuid.parse(command.ownerId))
            )
        } catch (e: IllegalArgumentException) {
            return AddListingResult.BadCommand(e.message ?: "Invalid command parameters")
        }

        listingRepository.add(listing)

        return AddListingResult.Success
    }
}