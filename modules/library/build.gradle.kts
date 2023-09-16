plugins {
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
}

version = "4.3.1-SNAPSHOT"

dependencies {
    // Kotlin
    commonMainImplementation(platform(kotlin("bom")))
    commonTestImplementation(kotlin("test"))

    // Kotlinx Serialization
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonTestImplementation(libs.kotlinx.serialization.json)
}
