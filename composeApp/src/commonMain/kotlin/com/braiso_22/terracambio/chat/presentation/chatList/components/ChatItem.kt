package com.braiso_22.terracambio.chat.presentation.chatList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.braiso_22.terracambio.chat.presentation.chat.components.bubble.ChatMessageType
import com.braiso_22.terracambio.chat.presentation.chat.components.bubble.Sender
import com.braiso_22.terracambio.chat.presentation.dateFormatting.toTimeOrRecentDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

data class ChatItem(
    val contactName: String,
    val lastMessage: ChatMessageType,
)

@OptIn(ExperimentalTime::class)
@Composable
fun ChatItem(
    state: ChatItem,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(state.contactName)
        },
        supportingContent = {
            Text(state.lastMessage.text)
        },
        trailingContent = {
            Text(state.lastMessage.date.toTimeOrRecentDate("yesterday"))
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ChatItemPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ChatItem(
                    state = ChatItem(
                        contactName = "Jane Doe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "toca hacer test",
                            sender = Sender.USER,
                            date = Clock.System.now()
                        )
                    )
                )
                ChatItem(
                    state = ChatItem(
                        contactName = "John Doe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "test de vaca",
                            sender = Sender.USER,
                            date = Clock.System.now() - 24.hours
                        )
                    )
                )
                ChatItem(
                    state = ChatItem(
                        contactName = "Pepe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "test",
                            sender = Sender.USER,
                            date = Clock.System.now() - 48.hours
                        )
                    )
                )
            }
        }
    }
}


