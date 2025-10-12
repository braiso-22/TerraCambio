package com.braiso_22.terracambio.listing.presentation.listingList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.Res
import terracambio.composeapp.generated.resources.add_listing_to_see
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ListingListComp(
    listings: List<ListingItem>,
    onClickItem: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (listings.isEmpty()) {
        Box(modifier = modifier) {
            Text(
                text = stringResource(Res.string.add_listing_to_see),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listings) {
                ListingItemComp(
                    listing = it,
                    onClick = onClickItem,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ListingListCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ListingListComp(
                    onClickItem = {},
                    listings = List(25) { index ->
                        ListingItem.example(index)
                    },
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun EmptyListingListCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ListingListComp(
                    onClickItem = {},
                    listings = List(0) { index ->
                        ListingItem.example(index)
                    },
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }
        }
    }
}


