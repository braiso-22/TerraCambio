package com.braiso_22.terracambio.listing.presentation.mapListings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.github.braiso_22.listing.vo.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
@Composable
actual fun MapPanel(
    originPlace: Location,
    listings: List<MapListingItem>,
    onClickMap: () -> Unit,
    onClickListing: (Uuid) -> Unit,
    modifier: Modifier,
) {

    val originPlace = LatLng(
        originPlace.geoLocation.latitude.value,
        originPlace.geoLocation.longitude.value
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(originPlace, 10f)
    }

    LaunchedEffect(originPlace) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(
            originPlace,
            cameraPositionState.position.zoom
        )
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
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapClick = { onClickMap() }
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
                    originPlace = Location.example,
                    listings = emptyList(),
                    onClickMap = {},
                    onClickListing = {},
                    modifier = Modifier
                )
            }
        }
    }
}