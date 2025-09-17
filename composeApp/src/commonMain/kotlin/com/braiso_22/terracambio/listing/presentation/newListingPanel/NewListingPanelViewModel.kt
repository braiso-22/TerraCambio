package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.lifecycle.ViewModel
import com.braiso_22.terracambio.listing.presentation.newListingForm.NewListingUserInteractions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewListingPanelViewModel(

) : ViewModel() {

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
    val transactions = _transactionsState.asStateFlow()

    fun onSave() {


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
            is NewListingUserInteractions.OnChangeCadastralCode -> onChangeNameCadastralCode(event.newCode)

            NewListingUserInteractions.OnCheckSell -> onCheckSell()

            is NewListingUserInteractions.OnChangeSellPrice -> onChangeSell(event.newPrice)

            NewListingUserInteractions.OnCheckRent -> onCheckRent()

            is NewListingUserInteractions.OnChangeRentPrice -> onChangeRentPrice(event.newPrice)

            NewListingUserInteractions.OnCheckSwitch -> onChangeSwitch()
        }
    }

    private fun onChangeNameCadastralCode(code: String) {
        _cadastralCodeState.update {
            when {
                code.length < 14 -> CadastralCodeState.InvalidFormat(code)
                else -> CadastralCodeState.Valid(code)
            }
        }
    }

    private fun onCheckSell() {
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

    private fun onChangeSell(newPrice: String) {
        _transactionsState.update { old ->
            old.copy(
                sellTransactionInfo = if (newPrice.isEmpty()) {
                    PriceTransactionState.InvalidPrice(newPrice)
                } else {
                    PriceTransactionState.ValidPrice(newPrice)
                }
            )
        }
    }

    private fun onCheckRent() {
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

    private fun onChangeRentPrice(newPrice: String) {
        _transactionsState.update { old ->
            old.copy(
                rentTransactionInfo = if (newPrice.isEmpty()) {
                    PriceTransactionState.InvalidPrice(newPrice)
                } else {
                    PriceTransactionState.ValidPrice(newPrice)
                }
            )
        }
    }

    private fun onChangeSwitch() {
        _transactionsState.update { old -> old.copy(isSwitch = !old.isSwitch) }
    }
}