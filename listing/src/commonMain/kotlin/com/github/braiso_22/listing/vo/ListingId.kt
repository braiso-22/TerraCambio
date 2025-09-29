package com.github.braiso_22.listing.vo

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@JvmInline
value class ListingId @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid) {
    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun example() = ListingId(Uuid.random())
    }
}