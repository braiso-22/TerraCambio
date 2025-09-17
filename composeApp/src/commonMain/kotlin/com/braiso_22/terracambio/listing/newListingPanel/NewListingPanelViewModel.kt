package com.braiso_22.terracambio.listing.newListingPanel

import androidx.lifecycle.ViewModel
import com.braiso_22.terracambio.listing.newListingForm.NewListingUserInteractions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewListingPanelViewModel : ViewModel() {

    private val _cadastralCodeState = MutableStateFlow<CadastralCodeState>(
        CadastralCodeState.Pristine
    )
    val cadastralCode = _cadastralCodeState.asStateFlow()

    private val _transactionsState = MutableStateFlow<TransactionsState>(
        TransactionsState(
            sellTransactionInfo = PriceTransactionState.Disabled,
            rentTransactionInfo = PriceTransactionState.Disabled,
            isSwitch = false
        )
    )
    val transations = _transactionsState.asStateFlow()

    fun onSave() {
        // restart by the moment
        _cadastralCodeState.update { CadastralCodeState.Pristine }
        _transactionsState.update {
            TransactionsState(
                sellTransactionInfo = PriceTransactionState.Disabled,
                rentTransactionInfo = PriceTransactionState.Disabled,
                isSwitch = false
            )
        }
    }

    fun onFormEvent(event: NewListingUserInteractions) {
        when (event) {
            is NewListingUserInteractions.OnChangeCadastralCode -> {
                val v = event.newName
                _cadastralCodeState.update {
                    when {
                        v.length < 14 -> CadastralCodeState.InvalidFormat(v)
                        else -> CadastralCodeState.Valid(v)
                    }
                }
            }

            NewListingUserInteractions.OnCheckSell -> {
                _transactionsState.update { old ->
                    val current = old.sellTransactionInfo
                    old.copy(
                        sellTransactionInfo = if (current is PriceTransactionState.Disabled) {
                            PriceTransactionState.ValidPrice("")
                        } else {
                            PriceTransactionState.Disabled
                        }
                    )
                }
            }

            is NewListingUserInteractions.OnChangeSellPrice -> {
                _transactionsState.update { old ->
                    val new = event.newPrice
                    old.copy(
                        sellTransactionInfo = if (new.isEmpty()) {
                            PriceTransactionState.InvalidPrice(new)
                        } else {
                            PriceTransactionState.ValidPrice(new)
                        }
                    )
                }
            }

            NewListingUserInteractions.OnCheckRent -> {
                _transactionsState.update { old ->
                    val current = old.rentTransactionInfo
                    old.copy(
                        rentTransactionInfo = if (current is PriceTransactionState.Disabled) {
                            PriceTransactionState.ValidPrice("")
                        } else {
                            PriceTransactionState.Disabled
                        }
                    )
                }
            }

            is NewListingUserInteractions.OnChangeRentPrice -> {
                val new = event.newPrice
                _transactionsState.update { old ->
                    old.copy(
                        rentTransactionInfo = if (new.isEmpty()) {
                            PriceTransactionState.InvalidPrice(new)
                        } else {
                            PriceTransactionState.ValidPrice(new)
                        }
                    )
                }
            }

            NewListingUserInteractions.OnCheckSwitch -> {
                _transactionsState.update { old -> old.copy(isSwitch = !old.isSwitch) }
            }
        }
    }
}