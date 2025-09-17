package com.github.braiso_22.listing.domain.vo

import kotlin.jvm.JvmInline

@JvmInline
value class ListingName(val value: String) {
    init {
        require(value.isNotBlank()) { "Listing name can't be empty" }
    }
}