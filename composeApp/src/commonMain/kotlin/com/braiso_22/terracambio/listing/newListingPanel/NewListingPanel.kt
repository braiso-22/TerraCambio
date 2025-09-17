package com.braiso_22.terracambio.listing.newListingPanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.listing.newListingForm.NewListingForm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.Res
import terracambio.composeapp.generated.resources.new_listing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewListingPanel(
    modifier: Modifier = Modifier,
    viewModel: NewListingPanelViewModel = NewListingPanelViewModel(),
) {
    val cadastralCode by viewModel.cadastralCode.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                stringResource(Res.string.new_listing)
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
        modifier = modifier
    ) {
        NewListingForm(
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
