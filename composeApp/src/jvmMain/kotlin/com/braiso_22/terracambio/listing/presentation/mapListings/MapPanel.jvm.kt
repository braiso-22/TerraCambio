package com.braiso_22.terracambio.listing.presentation.mapListings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(markerClass = [ExperimentalUuidApi::class])
@Composable
actual fun MapPanel(
    listings: List<MapListingItem>,
    onClickListing: (Uuid) -> Unit,
    modifier: Modifier,
) {
}