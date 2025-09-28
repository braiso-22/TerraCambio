package com.braiso_22.terracambio.listing.presentation.mapListings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
expect fun MapPanel(
    listings: List<MapListingItem>,
    onClickListing: (Uuid) -> Unit,
    modifier: Modifier,
)