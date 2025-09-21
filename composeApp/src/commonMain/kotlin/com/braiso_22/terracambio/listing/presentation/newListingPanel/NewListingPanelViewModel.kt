package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.braiso_22.terracambio.listing.application.port.`in`.ValidateCadastralCode
import com.braiso_22.terracambio.listing.application.port.`in`.ValidateCadastralCodeCommand
import com.braiso_22.terracambio.listing.application.port.`in`.ValidateCadastralCodeResult
import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListing
import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListingCommand
import com.braiso_22.terracambio.listing.application.port.`in`.addListing.AddListingResult
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.FakeLocationProvider
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.FakeUserLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingLocalDataSource
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.InMemoryListingServerDataSource
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.NewListingUserInteractions
import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewListingPanelViewModel(
    private val addListing: AddListing,
    private val validateCadastralCode: ValidateCadastralCode,
) : ViewModel() {

    private val _cadastralCodeState = MutableStateFlow<CadastralCodeState>(
        CadastralCodeState.Pristine
    )
    val cadastralCode = _cadastralCodeState.asStateFlow()

    private val _listingName = MutableStateFlow<String>("")
    val listingName = _listingName.asStateFlow()

    private val _transactions = MutableStateFlow<TransactionsState>(
        TransactionsState(
            sellTransactionInfo = PriceTransactionState.Disabled,
            rentTransactionInfo = PriceTransactionState.Disabled,
            isSwitch = false
        )
    )
    val transactions = _transactions.asStateFlow()


    val isSaveButtonEnabled = combine(
        listingName,
        transactions,
        cadastralCode
    ) { nameState, transactionsState, codeState ->
        nameState.isValidName() && transactionsState.areValidTransactions() && codeState.isValidCode()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _eventFlow = MutableSharedFlow<NewListingUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSave() {
        viewModelScope.launch {
            val commandListingName = try {
                ListingName(listingName.value)
            } catch (_: IllegalArgumentException) {
                _eventFlow.emit(NewListingUiEvent.BadName)
                return@launch
            }

            val commandTransactions = try {
                transactions.value.toDomain()
            } catch (_: IllegalArgumentException) {
                _transactions.update { it.withPristineAsError() }
                _eventFlow.emit(NewListingUiEvent.InvalidTransactions)
                return@launch
            }

            val commandCadastralCode = try {
                CadastralCode(cadastralCode.value.value)
            } catch (_: IllegalArgumentException) {
                _cadastralCodeState.update { CadastralCodeState.InvalidFormat(cadastralCode.value.value) }
                _eventFlow.emit(NewListingUiEvent.InvalidCadastralCode)
                return@launch
            }

            val result = addListing(
                command = AddListingCommand(
                    listingName = commandListingName,
                    transactions = commandTransactions,
                    cadastralCode = commandCadastralCode
                )
            )
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
            is NewListingUserInteractions.OnChangeCadastralCode -> onChangeCadastralCode(event.newCode)

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

    private var validationJob: Job? = null

    private fun onChangeCadastralCode(code: String) {
        validationJob?.cancel()

        val newCadastralCode = try {
            CadastralCode(code) // validation with domain logic
            CadastralCodeState.Valid(code)
        } catch (_: IllegalArgumentException) {
            CadastralCodeState.InvalidFormat(code)
        }

        _cadastralCodeState.update { newCadastralCode }

        if (newCadastralCode is CadastralCodeState.Valid) {
            _cadastralCodeState.update { CadastralCodeState.Loading(code) }
            validationJob = viewModelScope.launch {
                val result = validateCadastralCode(
                    command = ValidateCadastralCodeCommand(CadastralCode(code))
                )
                val finalState = when (result) {
                    ValidateCadastralCodeResult.NotFound -> CadastralCodeState.NotFound(code)
                    ValidateCadastralCodeResult.Valid -> CadastralCodeState.Valid(code)
                }

                _cadastralCodeState.update { finalState }
            }
        }
    }

    private fun onCheckSell() {
        _transactions.update { old ->
            val current = old.sellTransactionInfo
            old.copy(
                sellTransactionInfo = if (current is PriceTransactionState.Disabled) {
                    PriceTransactionState.Pristine
                } else {
                    PriceTransactionState.Disabled
                }
            )
        }
    }

    private fun onCheckRent() {
        _transactions.update { old ->
            val current = old.rentTransactionInfo
            old.copy(
                rentTransactionInfo = if (current is PriceTransactionState.Disabled) {
                    PriceTransactionState.Pristine
                } else {
                    PriceTransactionState.Disabled
                }
            )
        }
    }

    private fun onChangeSell(newPrice: String) {
        _transactions.update { old ->
            old.copy(sellTransactionInfo = newPrice.toPriceTransactionState())
        }
    }

    private fun onChangeRentPrice(newPrice: String) {
        _transactions.update { old ->
            old.copy(rentTransactionInfo = newPrice.toPriceTransactionState())
        }
    }

    private fun onChangeSwitch() {
        _transactions.update { old -> old.copy(isSwitch = !old.isSwitch) }
    }
}

private fun String.isValidName(): Boolean {
    return runCatching { ListingName(this) }.getOrNull() != null
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
            ),
            validateCadastralCode = ValidateCadastralCode(
                locationProvider = FakeLocationProvider(false)
            )
        )
    }
}