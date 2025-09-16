package com.braiso_22.terracambio_ui.listing.newListingPanel

import androidx.compose.runtime.Stable

@Stable
data class TransactionsState(
    val sellTransactionInfo: PriceTransactionState,
    val rentTransactionInfo: PriceTransactionState,
    val isSwitch: Boolean,
)

sealed interface PriceTransactionState {
    data object Disabled : PriceTransactionState
    open class Enabled(open val price: String) : PriceTransactionState

    data class ValidPrice(override val price: String) : Enabled(price = price)
    data class InvalidPrice(override val price: String) : Enabled(price=price)
}

sealed class CadastralCodeState(open val value: String) {
    data object Pristine : CadastralCodeState("")
    data class Loading(override val value: String) : CadastralCodeState(value)
    data class Valid(override val value: String) : CadastralCodeState(value)
    data class InvalidFormat(override val value: String) : CadastralCodeState(value)
    data class NotFound(override val value: String) : CadastralCodeState(value)
}
