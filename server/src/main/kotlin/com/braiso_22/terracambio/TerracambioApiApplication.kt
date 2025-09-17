package com.braiso_22.terracambio

import com.braiso_22.terracambio.listing.infrastructure.configuration.di.beans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TerracambioApiApplication

fun main(args: Array<String>) {
    runApplication<TerracambioApiApplication>(*args) {
        addInitializers(beans())
    }
}
