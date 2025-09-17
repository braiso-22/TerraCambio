plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.framework.boot)
    alias(libs.plugins.spring.dependency.management)
    application
}

group = "com.braiso_22"
version = "0.0.1-SNAPSHOT"
description = "Api for terracambio project"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.listing)
    implementation(libs.springboot.starter.web)

    // for coroutines and flows
    implementation(libs.springboot.starter.webflux)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.reactor)

    // serialization
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)

    // swagger-ui and openapi
    implementation(libs.spring.doc.openapi)

    // ktor client
    implementation("io.ktor:ktor-client-core:3.2.3")
    implementation("io.ktor:ktor-client-cio:3.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")
    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // testing
    developmentOnly(libs.springboot.dev.tools)
    testImplementation(libs.kotlin.testJunit)
}

application {
    mainClass.set("com.braiso_22.terracambio.TerracambioApiApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    //applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
