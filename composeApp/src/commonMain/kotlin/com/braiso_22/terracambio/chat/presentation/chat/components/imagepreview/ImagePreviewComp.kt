package com.braiso_22.terracambio.chat.presentation.chat.components.imagepreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImagePreviewComp(
    image: ImagePreview,
    onClickDelete: (ImagePreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.then(Modifier.size(64.dp))) {
        Box(
            modifier = Modifier.fillMaxSize().background(
                MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            IconButton(
                { onClickDelete(image) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Outlined.Cancel, "Delete image")
            }
        }
    }
}

@Preview
@Composable
private fun ImagePreviewCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ImagePreviewComp(
                    image = ImagePreview(""),
                    onClickDelete = {},
                    modifier = Modifier.size(90.dp)
                )
            }
        }
    }
}
