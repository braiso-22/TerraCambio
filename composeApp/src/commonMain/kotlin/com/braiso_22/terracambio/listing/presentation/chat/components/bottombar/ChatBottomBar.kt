package com.braiso_22.terracambio.listing.presentation.chat.components.bottombar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.listing.presentation.chat.components.imagepreview.ImagePreview
import com.braiso_22.terracambio.listing.presentation.chat.components.imagepreview.ImagePreviewRoster
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatBottomBar(
    text: String,
    onChangeText: (String) -> Unit,
    onSend: () -> Unit,
    images: List<ImagePreview>,
    onAddImage: () -> Unit,
    onCloseImage: (ImagePreview) -> Unit,
    modifier: Modifier = Modifier,
    onFocus: () -> Unit = {},
) {
    val source = remember {
        MutableInteractionSource()
    }

    val isPressed by source.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        onFocus()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            ImagePreviewRoster(
                images = images,
                onCloseImage = onCloseImage,
                modifier = modifier.fillMaxWidth().padding(8.dp),
            )
            OutlinedTextField(
                value = text,
                onValueChange = onChangeText,
                placeholder = {
                    Text("Message")
                },
                interactionSource = source,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            BottomAppBar(
                actions = {
                    IconButton(onAddImage) {
                        Icon(Icons.Outlined.AddPhotoAlternate, "Add image")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton({
                        if (text.isNotBlank()) {
                            onSend()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Send, "Send message")
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun ChatBottomBarPreview() {
    MaterialTheme {
        var text by remember { mutableStateOf("") }
        var images by remember {
            mutableStateOf(List(3) {
                ImagePreview(
                    it.toString()
                )
            })
        }
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ChatBottomBar(
                    text = text,
                    images = images,
                    onSend = {
                        text = ""
                        images = emptyList()
                    },
                    onChangeText = {
                        text = it
                    },
                    onFocus = {},
                    onAddImage = {
                        images = images + ImagePreview((images.lastIndex + 1).toString())
                    },
                    onCloseImage = { imageToClose ->
                        images = images.filter { it.text != imageToClose.text }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}
