package com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.comps.CadastralCodeComp
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.comps.TransactionTypeSelector
import com.braiso_22.terracambio.listing.presentation.newListingPanel.CadastralCodeState
import com.braiso_22.terracambio.listing.presentation.newListingPanel.CadastralCodeState.*
import com.braiso_22.terracambio.listing.presentation.newListingPanel.PriceTransactionState
import com.braiso_22.terracambio.listing.presentation.newListingPanel.PriceTransactionState.*
import com.braiso_22.terracambio.listing.presentation.newListingPanel.TransactionsState
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.comps.ListingNameComp
import com.github.braiso_22.listing.vo.ListingName
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NewListingForm(
    listingName: String,
    cadastralCodeState: CadastralCodeState,
    transactionsState: TransactionsState,
    onEvent: (NewListingUserInteractions) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ListingNameComp(
            state = listingName,
            onChangeCode = {
                onEvent(NewListingUserInteractions.OnChangeListingName(it))
            },
            modifier = Modifier
        )
        CadastralCodeComp(
            state = cadastralCodeState,
            onChangeCode = {
                onEvent(NewListingUserInteractions.OnChangeCadastralCode(it))
            },
            modifier = Modifier
        )
        TransactionTypeSelector(
            state = transactionsState,
            onCheckSell = { onEvent(NewListingUserInteractions.OnCheckSell) },
            onCheckRent = { onEvent(NewListingUserInteractions.OnCheckRent) },
            onCheckSwitch = { onEvent(NewListingUserInteractions.OnCheckSwitch) },
            onChangeSellPrice = { onEvent(NewListingUserInteractions.OnChangeSellPrice(it)) },
            onChangeRentPrice = { onEvent(NewListingUserInteractions.OnChangeRentPrice(it)) },
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun NewListingContentPreview() {
    MaterialTheme {
        Scaffold { paddingValues ->
            // Preview-local state that mimics screen state
            var cadastral by remember { mutableStateOf<CadastralCodeState>(CadastralCodeState.Pristine) }
            var listingName by remember { mutableStateOf<String>("") }
            var transactions by remember {
                mutableStateOf(
                    TransactionsState(
                        sellTransactionInfo = PriceTransactionState.Disabled,
                        rentTransactionInfo = PriceTransactionState.Disabled,
                        isSwitch = false
                    )
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                NewListingForm(
                    listingName = listingName,
                    cadastralCodeState = cadastral,
                    transactionsState = transactions,
                    onEvent = { event ->
                        when (event) {
                            is NewListingUserInteractions.OnChangeCadastralCode -> {
                                val v = event.newCode
                                cadastral = when {
                                    v.length < 14 -> InvalidFormat(v)
                                    else -> Valid(v)
                                }
                            }

                            NewListingUserInteractions.OnCheckSell -> {
                                val current = transactions.sellTransactionInfo
                                transactions = transactions.copy(
                                    sellTransactionInfo = if (current is PriceTransactionState.Disabled) {
                                        ValidPrice("")
                                    } else {
                                        PriceTransactionState.Disabled
                                    }
                                )
                            }

                            is NewListingUserInteractions.OnChangeSellPrice -> {
                                val new = event.newPrice
                                transactions = transactions.copy(
                                    sellTransactionInfo = if (new.isEmpty()) {
                                        InvalidPrice(new)
                                    } else {
                                        ValidPrice(new)
                                    }
                                )
                            }

                            NewListingUserInteractions.OnCheckRent -> {
                                val current = transactions.rentTransactionInfo
                                transactions = transactions.copy(
                                    rentTransactionInfo = if (current is PriceTransactionState.Disabled) {
                                        ValidPrice("")
                                    } else {
                                        PriceTransactionState.Disabled
                                    }
                                )
                            }

                            is NewListingUserInteractions.OnChangeRentPrice -> {
                                val new = event.newPrice
                                transactions = transactions.copy(
                                    rentTransactionInfo = if (new.isEmpty()) {
                                        InvalidPrice(new)
                                    } else {
                                        ValidPrice(new)
                                    }
                                )
                            }

                            NewListingUserInteractions.OnCheckSwitch -> {
                                transactions = transactions.copy(isSwitch = !transactions.isSwitch)
                            }

                            is NewListingUserInteractions.OnChangeListingName -> {
                                listingName = event.newName
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}