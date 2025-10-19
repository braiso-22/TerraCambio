package com.github.braiso_22.chat

import com.github.braiso_22.chat.vo.MessageContent
import com.github.braiso_22.chat.vo.MessageId
import com.github.braiso_22.chat.vo.Timestamp
import com.github.braiso_22.chat.vo.UserId

@ConsistentCopyVisibility
data class Message internal constructor(
    val id: MessageId,
    val authorId: UserId,
    val content: MessageContent,
    val timestamp: Timestamp,
)