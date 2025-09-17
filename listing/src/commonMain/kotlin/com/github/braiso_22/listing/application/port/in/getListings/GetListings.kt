package com.github.braiso_22.listing.application.port.`in`.getListings

import com.github.braiso_22.listing.domain.Listing

fun interface GetListings {
    suspend operator fun invoke(): List<Listing>
}

