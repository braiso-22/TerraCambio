package com.github.braiso_22.chat.vo

import kotlin.jvm.JvmInline
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@JvmInline
value class Timestamp @OptIn(ExperimentalTime::class) constructor(val value: Instant) {
    // Factory method to align with domain rules
    companion object {
        @OptIn(ExperimentalTime::class)
        fun now(clock: Clock): Timestamp {
            return Timestamp(clock.now())
        }
    }
}