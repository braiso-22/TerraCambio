package com.braiso_22.terracambio.listing.infrastructure.configuration.di

import com.braiso_22.terracambio.listing.infrastructure.adapters.output.httpClient
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.providers.FakeLocationProvider
import com.braiso_22.terracambio.listing.infrastructure.adapters.output.repository.InMemoryListingRepository
import com.braiso_22.terracambio.listing.application.input.addListing.AddListing
import com.braiso_22.terracambio.listing.application.input.getListings.GetListings
import com.braiso_22.terracambio.listing.application.input.validateCadastralCode.ValidateCadastralCode
import com.braiso_22.terracambio.listing.application.output.ListingRepository
import com.braiso_22.terracambio.listing.application.output.LocationProvider
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