plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "at.orchaldir.ofws"
version = "1.0.0"
application {
    mainClass = "at.orchaldir.ofws.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.logging)
    implementation(libs.logback)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.cors)
    implementation(ktorLibs.server.resources)
    testImplementation(ktorLibs.server.testHost)
    testImplementation(libs.kotlin.testJunit)
}