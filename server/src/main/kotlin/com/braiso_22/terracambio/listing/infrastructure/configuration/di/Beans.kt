package com.braiso_22.terracambio.listing.infrastructure.configuration.di

import com.braiso_22.terracambio.listing.application.AddListing
import com.braiso_22.terracambio.listing.application.GetListings
import com.braiso_22.terracambio.listing.application.ValidateCadastralCode
import com.braiso_22.terracambio.listing.infrastructure.adapters.out.httpClient
import com.braiso_22.terracambio.listing.infrastructure.adapters.out.providers.FakeLocationProvider
import com.braiso_22.terracambio.listing.infrastructure.adapters.out.repository.InMemoryListingRepository
import com.github.braiso_22.listing.application.port.`in`.addListing.AddListing
import com.github.braiso_22.listing.application.port.`in`.getListings.GetListings
import com.github.braiso_22.listing.application.port.`in`.validateCadastralCode.ValidateCadastralCode
import com.github.braiso_22.listing.application.port.out.ListingRepository
import com.github.braiso_22.listing.application.port.out.LocationProvider
import io.ktor.client.HttpClient
import org.springframework.context.support.beans


fun beans() = beans {
    bean<HttpClient> {
        httpClient
    }
    bean<LocationProvider> { FakeLocationProvider() }
    bean<ListingRepository> { InMemoryListingRepository() }
    bean<GetListings> {
        GetListings(listingRepository = ref())
    }
    bean<ValidateCadastralCode> {
        ValidateCadastralCode(
            locationProvider = ref()
        )
    }

    bean<AddListing> {
        AddListing(
            listingRepository = ref(),
            locationProvider = ref()
        )
    }
}