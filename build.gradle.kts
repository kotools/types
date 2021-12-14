import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin("jvm") version "1.5.31"
    `java-library`
}

group = "io.github.kotools"
version = "1.0.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation(platform(kotlin("bom")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")

    testImplementation(kotlin("test"))
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.32")
}

kotlin.explicitApi = ExplicitApiMode.Strict

tasks {
    compileJava { enabled = false }
    compileTestJava { enabled = false }
}
