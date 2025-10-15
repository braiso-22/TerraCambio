package com.braiso_22.terracambio.chat.presentation.chat.components.imagepreview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImagePreviewRoster(
    images: List<ImagePreview>,
    onCloseImage: (ImagePreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { image ->
            ImagePreviewComp(
                image = image,
                onClickDelete = onCloseImage,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun ImagePreviewRosterPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ImagePreviewRoster(
                    images = List(10) {
                        ImagePreview(it.toString())
                    },
                    onCloseImage = {},
                    modifier = Modifier.fillMaxSize().padding(8.dp)

                )
            }
        }
    }
}


