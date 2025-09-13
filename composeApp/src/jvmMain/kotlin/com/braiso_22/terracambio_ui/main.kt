package com.braiso_22.terracambio_ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TerraCambio",
    ) {
        App()
    }
}