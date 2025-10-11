package com.braiso_22.terracambio.listing.presentation.chat.components.bubble

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
    text: String,
    date: Instant,
    modifier: Modifier = Modifier,
) {
    val formattedDate = "${date.toLocalDateTime(TimeZone.currentSystemDefault()).hour}:${
        date.toLocalDateTime(
            TimeZone.currentSystemDefault()
        ).minute.toString().padStart(2, '0')
    }"

    // I add the formattedDate for hacking the position
    val textLines = text.split(" ", "\n") + formattedDate

    val containerColor = when (type) {
        BubbleType.SENT -> MaterialTheme.colorScheme.primaryContainer
        BubbleType.RECEIVED -> MaterialTheme.colorScheme.secondaryContainer
    }
    val contentColor = when (type) {
        BubbleType.SENT -> MaterialTheme.colorScheme.onPrimaryContainer
        BubbleType.RECEIVED -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Card(
        modifier = Modifier.align(
            when (type) {
                BubbleType.SENT -> Alignment.End
                BubbleType.RECEIVED -> Alignment.Start
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
                    BubbleType.RECEIVED,
                    "cacacacacaca cacacacaca cacacacacaca cacacacaca cacac aprubea",
                    Clock.System.now(),
                    modifier = Modifier.width(250.dp)
                )
                ChatBubble(
                    BubbleType.SENT,
                    """
                        Hello, how are you? ahahahahahahahahahaf aoaoao a aaaasdfasdfasjajjfjajfjajfajfajf
                    """.trimIndent(),
                    Clock.System.now(),
                    modifier = Modifier.width(250.dp)
                )
                ChatBubble(
                    BubbleType.SENT,
                    """
                        Hello, how are you? ahahahahahahahahahaf aoaoao a aaaasdfasdfasjajjfjajfjajfajfajfasd
                    """.trimIndent(),
                    Clock.System.now(),
                    modifier = Modifier.width(250.dp)
                )
            }
        }
    }
}


