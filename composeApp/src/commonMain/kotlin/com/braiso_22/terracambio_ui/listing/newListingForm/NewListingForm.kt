package com.braiso_22.terracambio_ui.listing.newListingForm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio_ui.listing.components.DropDownCheckBoxCard
import com.braiso_22.terracambio_ui.listing.components.PriceTextField
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*

@Composable
fun NewListingForm(
    state: NewListingFormState,
    onEvent: (NewListingUserInteractions) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.enter_cadastral_code),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(Res.string.where_to_find_cadastral_code),
            style = MaterialTheme.typography.bodyMedium,
        )
        OutlinedTextField(
            value = state.cadastralCode,
            onValueChange = {
                onEvent(NewListingUserInteractions.OnModifyListingName(it))
            },
            isError = state.invalidCadastralCode,
            label = {
                Text(text = stringResource(Res.string.cadastral_code))
            },
            supportingText = {
                Text(text = stringResource(Res.string.cadastral_code_requirement))
            },
            trailingIcon = {
                if (state.isLoadingCadastralCode) {
                    CircularProgressIndicator(
                        color = LocalContentColor.current,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp),
                    )
                } else if (state.invalidCadastralCode) {
                    Icon(Icons.Default.Error, null)
                } else {
                    Icon(Icons.Default.Check, null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(Res.string.transaction_type),
            style = MaterialTheme.typography.titleMedium,
        )
        DropDownCheckBoxCard(
            text = stringResource(Res.string.sell_transaction_type),
            checked = state.isSell,
            onChecked = {
                onEvent(NewListingUserInteractions.OnCheckSell)
            }
        ) {
            PriceTextField(
                value = state.sellPrice,
                onValueChange = { onEvent(NewListingUserInteractions.OnChangeSellPrice(it)) },
                invalidValue = state.invalidSellPrice,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }
        DropDownCheckBoxCard(
            text = stringResource(Res.string.rent_transaction_type),
            checked = state.isRent,
            onChecked = {
                onEvent(NewListingUserInteractions.OnCheckRent)
            }
        ) {
            PriceTextField(
                value = state.rentPrice,
                onValueChange = { onEvent(NewListingUserInteractions.OnChangeRentPrice(it)) },
                invalidValue = state.invalidRentPrice,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }
        DropDownCheckBoxCard(
            text = stringResource(Res.string.switch_transaction_type),
            checked = state.isSwitch,
            onChecked = {
                onEvent(NewListingUserInteractions.OnCheckSwitch)
            }
        )
    }
}

@Preview
@Composable
private fun NewListingContentPreview() {
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
                // Fake preview state and onEvent to preview behaviour
                var previewState by remember {
                    mutableStateOf(
                        NewListingFormState(
                            cadastralCode = "",
                            invalidCadastralCode = true,
                            isLoadingCadastralCode = false,
                            isSell = false,
                            sellPrice = "",
                            invalidSellPrice = false,
                            isRent = false,
                            rentPrice = "",
                            invalidRentPrice = false,
                            isSwitch = false,
                        )
                    )
                }
                NewListingForm(
                    state = previewState,
                    onEvent = { event ->
                        val s = previewState
                        previewState = when (event) {
                            is NewListingUserInteractions.OnModifyListingName -> s.copy(
                                cadastralCode = event.newName,
                                invalidCadastralCode = event.newName.length != 14
                            )

                            NewListingUserInteractions.OnCheckSell -> s.copy(isSell = !s.isSell)
                            is NewListingUserInteractions.OnChangeSellPrice -> s.copy(
                                sellPrice = event.newPrice,
                                invalidSellPrice = event.newPrice.isEmpty()
                            )

                            NewListingUserInteractions.OnCheckRent -> s.copy(isRent = !s.isRent)
                            is NewListingUserInteractions.OnChangeRentPrice -> s.copy(
                                rentPrice = event.newPrice,
                                invalidRentPrice = event.newPrice.isEmpty()
                            )

                            NewListingUserInteractions.OnCheckSwitch -> s.copy(isSwitch = !s.isSwitch)
                        }
                    },
                    Modifier.padding(16.dp)
                )
            }
        }
    }
}