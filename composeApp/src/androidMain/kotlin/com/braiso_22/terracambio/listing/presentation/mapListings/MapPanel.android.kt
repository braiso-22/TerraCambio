package com.braiso_22.terracambio.listing.presentation.mapListings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(markerClass = [ExperimentalUuidApi::class])
@Composable
actual fun MapPanel(
    listings: List<MapListingItem>,
    onClickListing: (Uuid) -> Unit,
    modifier: Modifier,
) {
    val originPlace = LatLng(43.1975400841553, -8.22239179787197)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(originPlace, 10f)
    }

    val properties = remember {
        MapProperties(
            mapType = MapType.SATELLITE,
        )
    }

    val uiSettings = remember {
        MapUiSettings()
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        listings.map { listing ->
            Marker(
                state = MarkerState(
                    position = LatLng(listing.latitude, listing.longitude)
                ),
                title = listing.title,
                snippet = listing.address,
                onClick = {
                    onClickListing(listing.id)
                    false
                }
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
private fun MapPanelPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                MapPanel(
                    listings = emptyList(),
                    onClickListing = {},
                    modifier = Modifier
                )
            }
        }
    }
}