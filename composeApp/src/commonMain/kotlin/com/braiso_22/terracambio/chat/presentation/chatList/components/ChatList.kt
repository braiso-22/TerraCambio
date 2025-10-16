package com.braiso_22.terracambio.chat.presentation.chatList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.braiso_22.terracambio.chat.presentation.chat.components.bubble.ChatMessageType
import com.braiso_22.terracambio.chat.presentation.chat.components.bubble.Sender
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@Composable
fun ChatList(
    items: List<ChatItem>,
    onClick: (ChatItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items, key = { it.id }) { item ->
            ChatItemComp(item, onClick = onClick)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ChatListPreview() {
    MaterialTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ChatList(
                    items = List(20) {
                        ChatItem(
                            id = "$it",
                            contactName = "$it Camille Little",
                            lastMessage = ChatMessageType.OnlyTextMessage(
                                text = "tristique $it",
                                sender = Sender.USER,
                                date = Clock.System.now() - it.days
                            )
                        )
                    },
                    onClick = {

                    }
                )
            }
        }
    }
}


