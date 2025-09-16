package com.braiso_22.terracambio_ui.listing.newListingForm.comps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio_ui.listing.components.DropDownCheckBoxCard
import com.braiso_22.terracambio_ui.listing.components.PriceTextField
import com.braiso_22.terracambio_ui.listing.newListingPanel.PriceTransactionState
import com.braiso_22.terracambio_ui.listing.newListingPanel.TransactionsState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*

@Composable
fun TransactionTypeSelector(
    state: TransactionsState,
    onCheckSell: (Boolean) -> Unit,
    onCheckRent: (Boolean) -> Unit,
    onCheckSwitch: (Boolean) -> Unit,
    onChangeSellPrice: (String) -> Unit,
    onChangeRentPrice: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.transaction_type),
            style = MaterialTheme.typography.titleMedium,
        )
        DropDownCheckBoxCard(
            text = stringResource(Res.string.sell_transaction_type),
            checked = state.sellTransactionInfo !is PriceTransactionState.Disabled,
            onChecked = onCheckSell
        ) {
            when (state.sellTransactionInfo) {
                is PriceTransactionState.Disabled -> {
                    null
                }

                is PriceTransactionState.Enabled -> {
                    PriceTextField(
                        value = state.sellTransactionInfo.price,
                        onValueChange = onChangeSellPrice,
                        invalidValue = state.sellTransactionInfo is PriceTransactionState.InvalidPrice,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            }

        }
        DropDownCheckBoxCard(
            text = stringResource(Res.string.rent_transaction_type),
            checked = state.rentTransactionInfo !is PriceTransactionState.Disabled,
            onChecked = onCheckRent
        ) {
            when (state.rentTransactionInfo) {
                is PriceTransactionState.Disabled -> {
                    null
                }

                is PriceTransactionState.Enabled -> {
                    PriceTextField(
                        value = state.rentTransactionInfo.price,
                        onValueChange = onChangeRentPrice,
                        invalidValue = state.rentTransactionInfo is PriceTransactionState.InvalidPrice,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            }
        }
        DropDownCheckBoxCard(
            text = stringResource(Res.string.switch_transaction_type),
            checked = state.isSwitch,
            onChecked = onCheckSwitch
        )
    }
}

@Preview
@Composable
fun TransactionTypeSelectorPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(it)
                    .fillMaxSize()
            ) {
                TransactionTypeSelector(
                    state = TransactionsState(
                        sellTransactionInfo = PriceTransactionState.InvalidPrice(""),
                        rentTransactionInfo = PriceTransactionState.ValidPrice("120"),
                        isSwitch = false
                    ),
                    onCheckSell = {},
                    onCheckRent = {},
                    onCheckSwitch = {},
                    onChangeSellPrice = {},
                    onChangeRentPrice = {},
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


