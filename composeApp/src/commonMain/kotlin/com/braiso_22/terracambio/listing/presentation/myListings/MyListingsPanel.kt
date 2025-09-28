package com.braiso_22.terracambio.listing.presentation.myListings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.braiso_22.terracambio.listing.presentation.listingList.ListingListComp
import com.braiso_22.terracambio.listing.presentation.mapListings.MapPanel
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun MyListingsPanel(
    modifier: Modifier = Modifier,
    viewModel: MyListingsPanelViewmodel = viewModel(
        factory = myListingsViewmodelFactory
    ),
) {
    val mapItems by viewModel.mapItems.collectAsState()
    val listingItems by viewModel.listingItems.collectAsState()
    val originPlace by viewModel.originPlace.collectAsState()

    Column(modifier = modifier) {
        MapPanel(
            originPlace = originPlace,
            listings = mapItems,
            onClickMap = viewModel::onDeselectListing,
            onClickListing = viewModel::onSelectListing,
            modifier = Modifier.height(250.dp)
        )
        ListingListComp(
            onClickItem = viewModel::onSelectListing,
            listings = listingItems,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun MyListingsPanelPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                MyListingsPanel()
            }
        }
    }
}


