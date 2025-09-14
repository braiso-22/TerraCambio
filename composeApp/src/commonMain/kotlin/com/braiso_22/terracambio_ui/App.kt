package com.braiso_22.terracambio_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio_ui.listing.newListingForm.CadastralCodeState
import com.braiso_22.terracambio_ui.listing.newListingForm.NewListingForm
import com.braiso_22.terracambio_ui.listing.newListingForm.NewListingUserInteractions
import com.braiso_22.terracambio_ui.listing.newListingForm.PriceTransactionState
import com.braiso_22.terracambio_ui.listing.newListingForm.TransactionsState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold { paddingValues ->
            // Preview-local state that mimics screen state
            var cadastral by remember { mutableStateOf<CadastralCodeState>(CadastralCodeState.Pristine) }
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
                    cadastralCodeState = cadastral,
                    transactionsState = transactions,
                    onEvent = { event ->
                        when (event) {
                            is NewListingUserInteractions.OnChangeCadastralCode -> {
                                val v = event.newName
                                cadastral = when {
                                    v.length < 14 -> CadastralCodeState.InvalidFormat(v)
                                    else -> CadastralCodeState.Valid(v)
                                }
                            }

                            NewListingUserInteractions.OnCheckSell -> {
                                val current = transactions.sellTransactionInfo
                                transactions = transactions.copy(
                                    sellTransactionInfo = if (current is PriceTransactionState.Disabled) {
                                        PriceTransactionState.ValidPrice("")
                                    } else {
                                        PriceTransactionState.Disabled
                                    }
                                )
                            }

                            is NewListingUserInteractions.OnChangeSellPrice -> {
                                val new = event.newPrice
                                transactions = transactions.copy(
                                    sellTransactionInfo = if (new.isEmpty()) {
                                        PriceTransactionState.InvalidPrice(new)
                                    } else {
                                        PriceTransactionState.ValidPrice(new)
                                    }
                                )
                            }

                            NewListingUserInteractions.OnCheckRent -> {
                                val current = transactions.rentTransactionInfo
                                transactions = transactions.copy(
                                    rentTransactionInfo = if (current is PriceTransactionState.Disabled) {
                                        PriceTransactionState.ValidPrice("")
                                    } else {
                                        PriceTransactionState.Disabled
                                    }
                                )
                            }

                            is NewListingUserInteractions.OnChangeRentPrice -> {
                                val new = event.newPrice
                                transactions = transactions.copy(
                                    rentTransactionInfo = if (new.isEmpty()) {
                                        PriceTransactionState.InvalidPrice(new)
                                    } else {
                                        PriceTransactionState.ValidPrice(new)
                                    }
                                )
                            }

                            NewListingUserInteractions.OnCheckSwitch -> {
                                transactions = transactions.copy(isSwitch = !transactions.isSwitch)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}