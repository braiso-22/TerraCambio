package com.braiso_22.terracambio.listing.application.port.`in`

import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingResponse
import com.braiso_22.terracambio.listing.application.port.out.ListingLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.ListingServerDataSource
import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.dtos.AddListingServerDto
import com.github.braiso_22.listing.domain.vo.*
import kotlin.uuid.ExperimentalUuidApi

class AddListingUseCase(
    private val listingLocalDataSource: ListingLocalDataSource,
    private val listingServerDataSource: ListingServerDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : AddListing {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(command: AddListingCommand): AddListingResult {

        val cadastralCode = try {
            CadastralCode(command.cadastralCode)
        } catch (_: IllegalArgumentException) {
            return AddListingResult.BadCadastralCode
        }

        val userId = userLocalDataSource.getUserId()

        val listing = try {
            AddListingServerDto(
                name = ListingName(command.listingName),
                listingTransactions = ListingTransactions(
                    command.transactions.map { it.toListingType() }.toSet()
                ),
                cadastralCode = cadastralCode,
                ownerId = OwnerId(userId.value)
            )
        } catch (e: IllegalArgumentException) {
            return AddListingResult.BadCommand(e.message ?: "Bad data while inserting")
        }

        val addListingResponse = listingServerDataSource.addListing(listing)

        return when (addListingResponse) {
            is AddListingResponse.BadCommand -> {
                AddListingResult.BadCommand(addListingResponse.message)
            }

            AddListingResponse.CadastralCodeNotFound -> {
                AddListingResult.BadCadastralCode
            }

            is AddListingResponse.Success -> {
                listingLocalDataSource.addListing(addListingResponse.listing)
                AddListingResult.Success
            }
        }
    }
}