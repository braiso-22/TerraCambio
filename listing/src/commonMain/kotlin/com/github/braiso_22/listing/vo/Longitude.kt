package com.github.braiso_22.listing.vo

import kotlin.jvm.JvmInline

@JvmInline
value class Longitude(val value: Double) {
    init {
        require(value.isFinite()) { "Longitude must be a finite number" }
        require(value in -180.0..180.0) { "Longitude must be in range [-180.0, 180.0]" }
    }
}