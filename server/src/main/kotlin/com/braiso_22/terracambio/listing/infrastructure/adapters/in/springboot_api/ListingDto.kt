package com.braiso_22.terracambio.listing.infrastructure.adapters.`in`.springboot_api

import com.github.braiso_22.listing.domain.vo.ListingTransactions
import com.github.braiso_22.listing.domain.vo.TransactionType
import io.swagger.v3.oas.annotations.media.Schema
import com.github.braiso_22.listing.application.port.`in`.CommandTransaction
import com.github.braiso_22.listing.application.port.`in`.CommandTransactionType
import com.github.braiso_22.listing.domain.Listing
import com.github.braiso_22.listing.domain.vo.Location
import kotlin.uuid.ExperimentalUuidApi

data class CreateListingDto(
    val listingName: String,
    val transactions: List<TransactionDto>,
    @param:Schema(minLength = 14, maxLength = 20)
    val cadastralCode: String,
    @param:Schema(format = "uuid")
    val ownerId: String,
)

data class ListingDto @OptIn(ExperimentalUuidApi::class) constructor(
    @param:Schema(format = "uuid")
    val id: String,
    val listingName: String,
    val transactions: List<TransactionDto>,
    val location: LocationDto,
    @param:Schema(format = "uuid")
    val ownerId: String,
)

@OptIn(ExperimentalUuidApi::class)
fun Listing.toDto(): ListingDto {
    return ListingDto(
        id = id.value.toHexString(),
        listingName = name.value,
        transactions = listingTransactions.toDto(),
        location = LocationDto.fromDomain(location),
        ownerId = ownerId.value.toHexString()
    )
}

fun List<TransactionDto>.toCommand(): List<CommandTransaction> {
    return this.map { it.toCommand() }
}

fun ListingTransactions.toDto(): List<TransactionDto> {
    return values.map {
        when (it) {
            is TransactionType.Buy -> TransactionDto(
                TransactionTypeDto.BUY,
                it.value.cents
            )

            is TransactionType.Rent -> TransactionDto(
                TransactionTypeDto.RENT,
                it.value.cents
            )

            TransactionType.Switch -> TransactionDto(
                TransactionTypeDto.SWITCH,
                null
            )
        }
    }
}

data class LocationDto(
    val latitude: Double,
    val longitude: Double,
    val name: String
) {
    companion object {
        fun fromDomain(location: Location): LocationDto {
            val geoLocation = location.geoLocation
            return LocationDto(
                latitude = geoLocation.latitude.value,
                longitude = geoLocation.longitude.value,
                name = location.name
            )
        }
    }
}

enum class TransactionTypeDto {
    BUY, RENT, SWITCH
}

data class TransactionDto(
    val type: TransactionTypeDto,
    val cents: Long?
) {
    fun toCommand(): CommandTransaction {
        return CommandTransaction(
            type = when (type) {
                TransactionTypeDto.BUY -> CommandTransactionType.BUY
                TransactionTypeDto.RENT -> CommandTransactionType.RENT
                TransactionTypeDto.SWITCH -> CommandTransactionType.SWITCH
            },
            cents = cents
        )
    }
}
