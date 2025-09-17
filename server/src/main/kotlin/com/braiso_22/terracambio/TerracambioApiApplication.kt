package com.braiso_22.terracambio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.support.beans

@ComponentScan("listing")
@SpringBootApplication
class TerracambioApiApplication

fun main(args: Array<String>) {
    runApplication<TerracambioApiApplication>(*args) {
        addInitializers(beans {})
    }
}
