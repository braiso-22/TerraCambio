package com.braiso_22.terracambio_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio_ui.listing.newListingForm.NewListingForm
import com.braiso_22.terracambio_ui.listing.newListingForm.NewListingFormState
import com.braiso_22.terracambio_ui.listing.newListingForm.NewListingUserInteractions
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
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
                            isLoadingCadastralCode = false,
                            invalidCadastralCode = false,
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