package com.github.braiso_22.listing.vo

import kotlin.jvm.JvmInline

sealed interface TransactionType {
    data class Buy(val value: Money) : TransactionType
    data class Rent(val value: Money) : TransactionType
    data object Switch : TransactionType
}

@JvmInline
value class ListingTransactions(val values: Set<TransactionType>) {
    init {
        require(values.isNotEmpty()) { "Listing must have at least one listing type" }
    }

    companion object {
        val buyExample = ListingTransactions(setOf(TransactionType.Buy(Money(100_000_00))))
        val rentExample = ListingTransactions(setOf(TransactionType.Rent(Money(500_00))))
        val switchExample = ListingTransactions(setOf(TransactionType.Switch))
        val allExample = ListingTransactions(
            setOf(
                TransactionType.Buy(Money(100_000_00)),
                TransactionType.Rent(Money(500_00)),
                TransactionType.Switch
            )
        )
    }
}