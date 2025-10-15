package com.braiso_22.terracambio.chat.presentation.chat.components.bubble

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface ChatMessageType {
    val text: String
    val sender: Sender

    @OptIn(ExperimentalTime::class)
    val date: Instant

    data class OnlyTextMessage @OptIn(ExperimentalTime::class) constructor(
        override val text: String,
        override val sender: Sender,
        override val date: Instant,
    ) : ChatMessageType

    data class ImageMessage @OptIn(ExperimentalTime::class) constructor(
        override val text: String,
        override val sender: Sender,
        override val date: Instant,
        val image: String,
    ) : ChatMessageType
}

enum class Sender {
    USER, OTHER
}