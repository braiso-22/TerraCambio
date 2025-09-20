package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.compose.runtime.Stable
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.Money
import com.github.braiso_22.listing.vo.TransactionType

@Stable
data class TransactionsState(
    val sellTransactionInfo: PriceTransactionState,
    val rentTransactionInfo: PriceTransactionState,
    val isSwitch: Boolean,
) {
    fun toDomain(): ListingTransactions {
        val transactions = buildSet {
            if (sellTransactionInfo is PriceTransactionState.ValidPrice) {
                add(TransactionType.Buy(sellTransactionInfo.toMoney()))
            }
            if (rentTransactionInfo is PriceTransactionState.ValidPrice) {
                add(TransactionType.Rent(rentTransactionInfo.toMoney()))
            }
            if (isSwitch) {
                add(TransactionType.Switch)
            }
        }

        return ListingTransactions(transactions)
    }
}

const val centsInCurrency = 100

sealed interface PriceTransactionState {
    data object Disabled : PriceTransactionState
    open class Enabled(open val price: String) : PriceTransactionState {
        fun toMoney(): Money {
            val parsedPrice = runCatching {
                price.replace(",", ".").toDouble()
            }.getOrNull() ?: 0.0

            val cents = (parsedPrice * centsInCurrency).toLong()
            return Money(cents)
        }
    }

    data class ValidPrice(override val price: String) : Enabled(price = price)
    data class InvalidPrice(override val price: String) : Enabled(price = price)
}

sealed class CadastralCodeState(open val value: String) {
    data object Pristine : CadastralCodeState("")
    data class Loading(override val value: String) : CadastralCodeState(value)
    data class Valid(override val value: String) : CadastralCodeState(value)
    data class InvalidFormat(override val value: String) : CadastralCodeState(value)
    data class NotFound(override val value: String) : CadastralCodeState(value)
}
