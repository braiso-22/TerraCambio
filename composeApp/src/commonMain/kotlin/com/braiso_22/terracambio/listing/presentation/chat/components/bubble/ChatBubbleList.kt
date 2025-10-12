package com.braiso_22.terracambio.listing.presentation.chat.components.bubble

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ChatBubbleList(
    chats: List<ChatItem>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(chats.size) {
        if (chats.isNotEmpty()) {
            listState.animateScrollToItem(index = chats.size - 1)
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(chats) { chatItem ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    ChatBubble(
                        item = ChatItem.OnlyTextMessage(
                            text = chatItem.text,
                            date = chatItem.date,
                            type = chatItem.type,
                        ),
                        modifier = Modifier.width(250.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ChatBubbleListPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ChatBubbleList(
                    List(30) {
                        ChatItem.OnlyTextMessage(
                            text = "text ${it} ".repeat(it + 1),
                            type = BubbleType.entries[it % 2],
                            date = Clock.System.now()
                        )
                    },
                    modifier = Modifier.padding(8.dp).fillMaxSize()
                )
            }
        }
    }
}


