package com.github.braiso_22.listing.vo

import kotlin.jvm.JvmInline

@JvmInline
value class Money(val cents: Long) {
    init {
        require(cents > 0) { "Money must be greater than 0.0" }
    }

    fun toDecimal(): Double = cents.toDouble() / 100.0
}