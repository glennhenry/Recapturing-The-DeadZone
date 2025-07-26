
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "dev.deadzone"
version = "2025.07.11"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(libs.ktor.network.tls)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)
	implementation(libs.ktor.serialization.kotlinx.protobuf)
	implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.mongodb.driver.kotlin.coroutine)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    implementation("io.github.classgraph:classgraph:4.8.181")
}
