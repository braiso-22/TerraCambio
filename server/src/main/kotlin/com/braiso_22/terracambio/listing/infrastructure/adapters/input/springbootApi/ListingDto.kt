package com.braiso_22.terracambio.listing.infrastructure.adapters.input.springbootApi

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.Location
import com.github.braiso_22.listing.vo.Money
import com.github.braiso_22.listing.vo.TransactionType
import io.swagger.v3.oas.annotations.media.Schema
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

fun List<TransactionDto>.toDomain(): Set<TransactionType> {
    return this.map { it.toDomain() }.toSet()
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
    val cadastralCode: String,
    val name: String,
) {
    companion object {
        fun fromDomain(location: Location): LocationDto {
            val geoLocation = location.geoLocation
            return LocationDto(
                latitude = geoLocation.latitude.value,
                longitude = geoLocation.longitude.value,
                cadastralCode = location.cadastralCode.value,
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
    val cents: Long?,
) {
    fun toDomain(): TransactionType {
        return when (type) {
            TransactionTypeDto.BUY -> TransactionType.Buy(
                Money(cents ?: 0)
            )

            TransactionTypeDto.RENT -> TransactionType.Rent(
                Money(cents ?: 0)
            )

            TransactionTypeDto.SWITCH -> TransactionType.Switch
        }

    }
}
