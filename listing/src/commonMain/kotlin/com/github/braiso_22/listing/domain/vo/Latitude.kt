package com.github.braiso_22.listing.domain.vo

import kotlin.jvm.JvmInline


@JvmInline
value class Latitude(val value: Double) {
    init {
        require(value.isFinite()) { "Latitude must be a finite number" }
        require(value in -90.0..90.0) { "Latitude must be in range [-90.0, 90.0]" }
    }
}