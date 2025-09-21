package com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.comps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.listing.presentation.newListingPanel.CadastralCodeState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*

@Composable
fun ListingNameComp(
    state: String,
    onChangeCode: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = state,
        onValueChange = onChangeCode,
        isError = state.isBlank(),
        label = {
            Text(text = stringResource(Res.string.listing_name))
        },
        supportingText = {
            Text(text = stringResource(Res.string.cant_be_empty))
        },
        singleLine = true,
        modifier = modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun ListingNameCompPreview() {
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
                ListingNameComp(
                    state = "Fill",
                    onChangeCode = {},
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


