package com.braiso_22.terracambio.listing.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


enum class BubbleType {
    SENT, RECEIVED
}

@OptIn(ExperimentalTime::class)
@Composable
fun ColumnScope.ChatBubble(
    type: BubbleType,
    textLines: List<String>,
    date: Instant,
    modifier: Modifier = Modifier,
) {
    val bubbleContent: @Composable () -> Unit = {
        FlowRow(
            modifier = Modifier.padding(8.dp)
        ) {
            textLines.forEachIndexed { index, text ->
                if (index == textLines.lastIndex) {
                    FlowRow(
                        itemVerticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "${date.toLocalDateTime(TimeZone.currentSystemDefault()).hour}:${
                                date.toLocalDateTime(
                                    TimeZone.currentSystemDefault()
                                ).minute.toString().padStart(2, '0')
                            }",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                        )
                    }
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

        }
    }

    val _modifier = Modifier
        .align(
            when (type) {
                BubbleType.SENT -> Alignment.End
                BubbleType.RECEIVED -> Alignment.Start
            }
        )
        .then(modifier)
    when (type) {
        BubbleType.SENT -> OutlinedCard(modifier = _modifier) { bubbleContent() }
        BubbleType.RECEIVED -> Card(modifier = _modifier) { bubbleContent() }
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
                    .fillMaxSize()
            ) {
                ChatBubble(
                    BubbleType.RECEIVED,
                    listOf(
                        "Hello, how are you?",
                        ":("
                    ),
                    Clock.System.now(),
                    modifier = Modifier.padding(8.dp).width(250.dp)
                )
                ChatBubble(
                    BubbleType.SENT,
                    listOf(
                        "Hello, how are you?",
                        "ahahahahahahahahahaf",
                        "aoaoao", "aaaasdfasdfasjajjfjajfjajfajfajfajf",
                    ),
                    Clock.System.now(),
                    modifier = Modifier.padding(8.dp).width(250.dp)
                )
            }
        }
    }
}


