package com.braiso_22.terracambio.listing.presentation.mapListings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.braiso_22.listing.vo.Location
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
expect fun MapPanel(
    originPlace: Location,
    listings: List<MapListingItem>,
    onClickMap: () -> Unit,
    onClickListing: (Uuid) -> Unit,
    modifier: Modifier,
)