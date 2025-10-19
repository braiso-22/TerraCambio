package clock

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class FixedClock @OptIn(ExperimentalTime::class) constructor(
    private var fixedInstant: Instant,
) : Clock {
    override fun now(): Instant = fixedInstant
    fun add(duration: Duration) {
        fixedInstant = fixedInstant.plus(duration)
    }
}