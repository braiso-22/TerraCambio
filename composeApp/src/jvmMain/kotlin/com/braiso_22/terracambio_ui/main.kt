package com.braiso_22.terracambio_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.braiso_22.terracambio_ui.listing.newListingPanel.NewListingPanel
import org.jetbrains.compose.ui.tooling.preview.Preview

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TerraCambio",
    ) {
        AppDesktop()
    }
}


@Preview
@Composable
fun AppDesktop() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            // Box will fill the size of the Surface, but it gives its children
            // loose constraints, allowing them to choose their own size.
            Box(
                contentAlignment = Alignment.Center, // Optional: center the panel
                modifier = Modifier.fillMaxSize()
            ) {
                NewListingPanel(
                    modifier = Modifier.width(500.dp)
                )
            }
        }
    }
}