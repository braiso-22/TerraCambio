package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.NewListingForm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.Res
import terracambio.composeapp.generated.resources.new_listing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewListingPanel(
    onEvent: (event: NewListingUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewListingPanelViewModel = viewModel(
        factory = mainViewModelFactory,
    ),
) {
    val cadastralCode by viewModel.cadastralCode.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    val listingName by viewModel.listingName.collectAsState()
    val isSaveButtonEnabled by viewModel.isSaveButtonEnabled.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            onEvent(event)
        }
    }

    Column(modifier) {
        NewListingForm(
            listingName = listingName,
            cadastralCodeState = cadastralCode,
            transactionsState = transactions,
            onEvent = viewModel::onFormEvent,
            modifier = Modifier.weight(1f)
                .verticalScroll(rememberScrollState())
        )
        Button(
            onClick = viewModel::onSave,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isSaveButtonEnabled) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                } else {
                    MaterialTheme.colorScheme.primary
                },
                contentColor = if (!isSaveButtonEnabled) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.32f)
                } else {
                    MaterialTheme.colorScheme.onPrimary
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(Res.string.new_listing))
        }
    }
}

@Preview
@Composable
fun NewListingScreenPreview() {
    MaterialTheme {
        Surface {
            Scaffold {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    NewListingPanel({}, Modifier)
                }
            }
        }
    }
}
