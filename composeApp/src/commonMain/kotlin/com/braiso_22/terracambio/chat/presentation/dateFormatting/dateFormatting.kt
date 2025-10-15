package com.braiso_22.terracambio.chat.presentation.dateFormatting

import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
private fun Instant.toTimeWith4Numbers(): String {
    val formattedDate = "${this.toLocalDateTime(TimeZone.currentSystemDefault()).hour}:${
        this.toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).minute.toString().padStart(2, '0')
    }"
    return formattedDate

}


@OptIn(ExperimentalTime::class)
fun Instant.toTimeOrRecentDate(
    localizedYesterday: String,
): String {
    val dateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val today = now.date
    val yesterday = today.minus(DatePeriod(days = 1))

    return when (dateTime.date) {
        today -> this.toTimeWith4Numbers()
        yesterday -> localizedYesterday
        else -> "${dateTime.day}/${dateTime.month.number}/${dateTime.year}"
    }
}