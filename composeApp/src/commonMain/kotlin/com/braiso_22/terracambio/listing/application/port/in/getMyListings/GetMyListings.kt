package com.braiso_22.terracambio.listing.application.port.`in`.getMyListings

import com.github.braiso_22.listing.Listing

fun interface GetMyListings {
    suspend operator fun invoke(): List<Listing>
}