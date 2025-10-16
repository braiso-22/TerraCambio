package com.braiso_22.terracambio.chat.presentation.chatList.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
    val id: String,
    val contactName: String,
    val lastMessage: ChatMessageType,
)

@OptIn(ExperimentalTime::class, ExperimentalFoundationApi::class)
@Composable
fun ChatItemComp(
    state: ChatItem,
    onClick: (ChatItem) -> Unit,
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
        modifier = modifier.clickable {
            onClick(state)
        },
    )
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ChatItemPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                ChatItemComp(
                    state = ChatItem(
                        id = "3",
                        contactName = "Jane Doe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "toca hacer test",
                            sender = Sender.USER,
                            date = Clock.System.now()
                        )
                    ),
                    onClick = {},
                )
                ChatItemComp(
                    state = ChatItem(
                        id = "2",
                        contactName = "John Doe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "test de vaca",
                            sender = Sender.USER,
                            date = Clock.System.now() - 24.hours
                        )
                    ),
                    onClick = {},
                )
                ChatItemComp(
                    state = ChatItem(
                        id = "1",
                        contactName = "Pepe",
                        lastMessage = ChatMessageType.OnlyTextMessage(
                            text = "test",
                            sender = Sender.USER,
                            date = Clock.System.now() - 48.hours
                        )
                    ),
                    onClick = {},
                )
            }
        }
    }
}


