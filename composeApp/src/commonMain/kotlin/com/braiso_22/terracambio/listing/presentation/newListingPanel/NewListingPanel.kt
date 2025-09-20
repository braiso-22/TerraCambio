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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.NewListingForm
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.Res
import terracambio.composeapp.generated.resources.bad_format
import terracambio.composeapp.generated.resources.cadastral_code_not_found
import terracambio.composeapp.generated.resources.could_not_create
import terracambio.composeapp.generated.resources.listing_created
import terracambio.composeapp.generated.resources.new_listing

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
    val scope = rememberCoroutineScope()


    val badFormat = stringResource(Res.string.bad_format)
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
                        NewListingUiEvent.BadFormat -> badFormat

                        NewListingUiEvent.CadastralCodeNotFound -> cadastralCodeNotFound

                        NewListingUiEvent.CouldNotCreate -> couldNotCreateListing

                        NewListingUiEvent.ListingCreated -> listingCreated
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
                .padding(16.dp)
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
                        .fillMaxSize()
                ) {
                    NewListingPanel()
                }
            }
        }
    }
}
