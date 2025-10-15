package com.braiso_22.terracambio.chat.presentation.chat.components.bubble

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.chat.presentation.dateFormatting.toTimeOrRecentDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun ColumnScope.ChatBubble(
    item: ChatMessageType,
    modifier: Modifier = Modifier,
) {
    val formattedDate = item.date.toTimeOrRecentDate("yesterday")

    // I add the formattedDate for hacking the position
    val textLines = item.text.split(" ", "\n") + formattedDate

    val containerColor = when (item.sender) {
        Sender.USER -> MaterialTheme.colorScheme.primaryContainer
        Sender.OTHER -> MaterialTheme.colorScheme.secondaryContainer
    }
    val contentColor = when (item.sender) {
        Sender.USER -> MaterialTheme.colorScheme.onPrimaryContainer
        Sender.OTHER -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Card(
        modifier = Modifier.align(
            when (item.sender) {
                Sender.USER -> Alignment.End
                Sender.OTHER -> Alignment.Start
            }
        ).then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                textLines.forEachIndexed { index, text ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier,
                        // last item is the date, has to be same color of container to
                        // hack the position
                        color = if (index == textLines.lastIndex)
                            containerColor
                        else
                            contentColor
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 2.dp)
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ChatBubblePreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChatBubble(
                    item = ChatMessageType.OnlyTextMessage(
                        "Testing this chat a lot! :)",
                        Sender.OTHER,
                        Clock.System.now(),
                    ),
                    modifier = Modifier.width(250.dp)
                )
                ChatBubble(
                    item = ChatMessageType.OnlyTextMessage(
                        "Sure this chat is incredible, how did you do it???????????😮",
                        Sender.USER,
                        Clock.System.now(),
                    ),
                    modifier = Modifier.width(250.dp)
                )
                ChatBubble(
                    item = ChatMessageType.OnlyTextMessage(
                        "I mean how did you start, how did you think about " +
                                "implementing this, this chat is a nonsense hehe",
                        Sender.USER,
                        Clock.System.now(),
                    ),
                    modifier = Modifier.width(250.dp)
                )
            }
        }
    }
}


