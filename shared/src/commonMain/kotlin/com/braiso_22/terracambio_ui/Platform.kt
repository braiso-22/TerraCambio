package com.braiso_22.terracambio_ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform