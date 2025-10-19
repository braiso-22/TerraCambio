package com.github.braiso_22.chat

import com.github.braiso_22.chat.vo.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Chat(
    val id: ChatId,
    val participants: Set<UserId>,
    var lastMessageTimestamp: Timestamp?,
) {
    @OptIn(ExperimentalTime::class)
    fun addMessage(
        authorId: UserId,
        content: MessageContent,
        messageId: MessageId,
        clock: Clock,
    ): Message {
        require(participants.contains(authorId)) {
            "Message author must be a participant"
        }

        val timestamp = Timestamp.now(clock)
        this.lastMessageTimestamp = timestamp

        return Message(
            id = messageId,
            authorId = authorId,
            content = content,
            timestamp = timestamp,
        )
    }
}