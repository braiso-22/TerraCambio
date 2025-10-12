package com.braiso_22.terracambio.listing.presentation.chat.components.bubble

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface ChatItem {
    val text: String
    val type: BubbleType

    @OptIn(ExperimentalTime::class)
    val date: Instant

    data class OnlyTextMessage @OptIn(ExperimentalTime::class) constructor(
        override val text: String,
        override val type: BubbleType,
        override val date: Instant,
    ) : ChatItem

    data class ImageMessage @OptIn(ExperimentalTime::class) constructor(
        override val text: String,
        override val type: BubbleType,
        override val date: Instant,
        val image: String,
    ) : ChatItem
}

enum class BubbleType {
    SENT, RECEIVED
}