package com.braiso_22.terracambio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform