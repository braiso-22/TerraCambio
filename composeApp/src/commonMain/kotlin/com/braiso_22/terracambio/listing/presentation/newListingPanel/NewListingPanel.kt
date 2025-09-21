package com.braiso_22.terracambio.listing.presentation.newListingPanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.NewListingForm
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewListingPanel(
    modifier: Modifier = Modifier,
    viewModel: NewListingPanelViewModel = viewModel(
        factory = mainViewModelFactory,
    ),
) {
    val cadastralCode by viewModel.cadastralCode.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    val listingName by viewModel.listingName.collectAsState()
    val isSaveButtonEnabled by viewModel.isSaveButtonEnabled.collectAsState()

    val scope = rememberCoroutineScope()

    val badName = stringResource(Res.string.bad_name)
    val badCadastralCode = stringResource(Res.string.bad_cadastral_code)
    val badTransactions = stringResource(Res.string.bad_transactions)
    val cadastralCodeNotFound = stringResource(Res.string.cadastral_code_not_found)
    val couldNotCreateListing = stringResource(Res.string.could_not_create)
    val listingCreated = stringResource(Res.string.listing_created)

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    when (event) {
                        NewListingUiEvent.CadastralCodeNotFound -> cadastralCodeNotFound
                        NewListingUiEvent.CouldNotCreate -> couldNotCreateListing
                        NewListingUiEvent.ListingCreated -> listingCreated
                        NewListingUiEvent.BadName -> badName
                        NewListingUiEvent.InvalidCadastralCode -> badCadastralCode
                        NewListingUiEvent.InvalidTransactions -> badTransactions
                    }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(Res.string.new_listing))
            })
        },
        bottomBar = {
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier
    ) {
        NewListingForm(
            listingName = listingName,
            cadastralCodeState = cadastralCode,
            transactionsState = transactions,
            onEvent = viewModel::onFormEvent,
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        )
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
                    NewListingPanel()
                }
            }
        }
    }
}
