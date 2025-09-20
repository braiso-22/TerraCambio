package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.braiso_22.terracambio.listing.application.port.`in`.AddListing
import com.braiso_22.terracambio.listing.application.port.`in`.AddListingCommand
import com.braiso_22.terracambio.listing.application.port.`in`.AddListingResult
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.FakeUserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingServerDataSource
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.NewListingUserInteractions
import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewListingPanelViewModel(
    private val addListing: AddListing,
) : ViewModel() {

    private val _cadastralCodeState = MutableStateFlow<CadastralCodeState>(
        CadastralCodeState.Pristine
    )
    val cadastralCode = _cadastralCodeState.asStateFlow()

    private val _listingName = MutableStateFlow<String>("")
    val listingName = _listingName.asStateFlow()

    private val _transactionsState = MutableStateFlow<TransactionsState>(
        TransactionsState(
            sellTransactionInfo = PriceTransactionState.Disabled,
            rentTransactionInfo = PriceTransactionState.Disabled,
            isSwitch = false
        )
    )
    val transactions = _transactionsState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<NewListingUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSave() {
        viewModelScope.launch {
            val command = try {
                AddListingCommand(
                    listingName = ListingName(listingName.value),
                    transactions = transactions.value.toDomain(),
                    cadastralCode = CadastralCode(listingName.value)
                )
            } catch (_: IllegalArgumentException) {
                _eventFlow.emit(NewListingUiEvent.BadFormat)
                return@launch
            }

            val result = addListing(command = command)
            when (result) {
                AddListingResult.BadCadastralCode -> {
                    _eventFlow.emit(NewListingUiEvent.CadastralCodeNotFound)
                }

                is AddListingResult.BadCommand -> {
                    _eventFlow.emit(NewListingUiEvent.CouldNotCreate)

                }

                AddListingResult.Success -> {
                    _eventFlow.emit(NewListingUiEvent.ListingCreated)
                }
            }
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
            is NewListingUserInteractions.OnChangeListingName -> {
                _listingName.update { event.newName }
            }
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

    private fun onChangeSell(newPrice: String) {
        _transactionsState.update { old ->
            old.copy(sellTransactionInfo = newPrice.toPriceTransactionState())
        }
    }

    private fun onChangeRentPrice(newPrice: String) {
        _transactionsState.update { old ->
            old.copy(rentTransactionInfo = newPrice.toPriceTransactionState())
        }
    }

    private fun onChangeSwitch() {
        _transactionsState.update { old -> old.copy(isSwitch = !old.isSwitch) }
    }
}


private fun String.isValidPrice(): Boolean {
    // only numbers before and after a "," or "."
    val regex = Regex("^\\d+(?:[.,]\\d+)?$")
    return this.isBlank() || !regex.matches(this)
}

private fun String.toPriceTransactionState(): PriceTransactionState = if (this.isValidPrice()) {
    PriceTransactionState.InvalidPrice(this)
} else {
    PriceTransactionState.ValidPrice(this)
}


val mainViewModelFactory = viewModelFactory {
    initializer {
        NewListingPanelViewModel(
            addListing = AddListing(
                listingLocalDataSource = InMemoryListingLocalDataSource(),
                listingServerDataSource = InMemoryListingServerDataSource(),
                userLocalDataSource = FakeUserLocalDataSource(),
            )
        )
    }
}