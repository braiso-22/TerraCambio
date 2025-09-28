package com.github.braiso_22.listing.vo

import kotlin.jvm.JvmInline

@JvmInline
value class ListingName(val value: String) {
    init {
        require(value.isNotBlank()) { "Listing name can't be empty" }
    }

    companion object {
        val example = ListingName("I sell my house")
    }
}