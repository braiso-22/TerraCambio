package com.braiso_22.terracambio.listing.presentation.myListings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.braiso_22.terracambio.listing.presentation.listingList.ListingItem
import com.braiso_22.terracambio.listing.presentation.mapListings.MapListingItem
import com.github.braiso_22.listing.Listing
import com.github.braiso_22.listing.vo.*
import kotlinx.coroutines.flow.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MyListingsPanelViewmodel : ViewModel() {


    @OptIn(ExperimentalUuidApi::class)
    private val ownerId = OwnerId(Uuid.random())

    @OptIn(ExperimentalUuidApi::class)
    private val _listings = MutableStateFlow(
        List(10) {
            Listing.exampleWithIdAndOwner(
                id = ListingId(Uuid.random()),
                ownerId = ownerId
            ).copy(
                location = Location.example.copy(
                    geoLocation = Location.example.geoLocation.copy(
                        latitude = Latitude(it.toDouble()),
                        longitude = Longitude(it.toDouble())
                    )
                )
            )
        }
    )

    @OptIn(ExperimentalUuidApi::class)
    private val _selectedListingId = MutableStateFlow<Uuid?>(null)

    @OptIn(ExperimentalUuidApi::class)
    val mapItems = _listings.map { listings ->
        listings.map { listing ->
            MapListingItem.fromDomain(listing)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    @OptIn(ExperimentalUuidApi::class)
    val listingItems = combine(_listings, _selectedListingId) { listings, selectedId ->
        listings.map { listing ->
            val isSelected = listing.id.value == selectedId
            ListingItem.fromDomain(listing).copy(
                isSelected = isSelected
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    @OptIn(ExperimentalUuidApi::class)
    val originPlace = combine(_listings, _selectedListingId) { listings, selectedId ->
        val selectedListing = listings.find { it.id.value == selectedId }
        selectedListing?.location ?: Location.example
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Location.example
    )

    @OptIn(ExperimentalUuidApi::class)
    fun onSelectListing(listingId: Uuid) {
        _selectedListingId.update { old ->
            if (old == listingId)
                null
            else
                listingId
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onDeselectListing() {
        _selectedListingId.update { null }
    }
}

val myListingsViewmodelFactory = viewModelFactory {
    initializer {
        MyListingsPanelViewmodel()
    }
}