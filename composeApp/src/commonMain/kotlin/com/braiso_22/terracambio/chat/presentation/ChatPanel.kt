package com.braiso_22.terracambio.chat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.chat.presentation.components.bottombar.ChatBottomBar
import com.braiso_22.terracambio.chat.presentation.components.bubble.BubbleType
import com.braiso_22.terracambio.chat.presentation.components.bubble.ChatBubbleList
import com.braiso_22.terracambio.chat.presentation.components.bubble.ChatItem
import com.braiso_22.terracambio.chat.presentation.components.imagepreview.ImagePreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatPanel(
    modifier: Modifier = Modifier,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val isKeyboardOpen by keyboardAsState()
    var isTextFieldFocused by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardOpen, isTextFieldFocused) {
        if (isKeyboardOpen && isTextFieldFocused) {
            bringIntoViewRequester.bringIntoView()
        }
    }
    var text by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(emptyList<ChatItem>()) }
    var images by remember { mutableStateOf(emptyList<ImagePreview>()) }


    Column(
        modifier = modifier,
    ) {
        ChatBubbleList(
            chats = messages,
            modifier = Modifier.fillMaxSize().weight(1f)
                .padding(8.dp)
        )
        ChatBottomBar(
            text = text,
            onChangeText = { text = it },
            onSend = {
                messages = messages + ChatItem.OnlyTextMessage(
                    text = text,
                    type = BubbleType.SENT,
                    date = Clock.System.now()
                ) + ChatItem.OnlyTextMessage(
                    text = "I don't like `$text`",
                    type = BubbleType.RECEIVED,
                    date = Clock.System.now()
                )
                text = ""
                images = emptyList()
            },
            images = images,
            onAddImage = {
                images = images + ImagePreview(images.size.toString())
            },
            onCloseImage = { imageToClose ->
                images = images.filter { it.text != imageToClose.text }
            },
            modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
            onFocus = {
                isTextFieldFocused = true
            }
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Preview
@Composable
fun ChatPanelPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ChatPanel()
            }
        }
    }
}
