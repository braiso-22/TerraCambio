package com.braiso_22.terracambio.listing.presentation.listingList

import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.ListingTransactions
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ListingItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val name: String,
    val location: String,
    val isSelected: Boolean,
    val transactions: ListingTransactions,
) {
    companion object {

        @OptIn(ExperimentalUuidApi::class)
        fun fromDomain(listing: Listing): ListingItem {
            return ListingItem(
                id = listing.id.value,
                name = listing.name.value,
                location = listing.location.name,
                isSelected = false,
                transactions = listing.listingTransactions
            )
        }

        @OptIn(ExperimentalUuidApi::class)
        fun example(index: Int) = ListingItem(
            id = Uuid.random(),
            name = "Listing $index",
            location = "Location ${index * index * index * 10}, Some very long address that might overflow",
            isSelected = index == 0,
            transactions = when (index % 4) {
                0 -> ListingTransactions.buyExample
                1 -> ListingTransactions.rentExample
                2 -> ListingTransactions.switchExample
                else -> ListingTransactions.allExample
            },
        )
    }
}