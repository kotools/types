import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin(module = "jvm") version "1.5.32"
    `maven-publish`
}

group = "com.github.kotools"
version = "0.0.0"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    setOf(
        "com.github.doyaaaaaken:kotlin-csv-jvm:[1.2, 1.3[",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:[1.5, 1.6["
    ).forEach(::implementation)
}

publishing {
    repositories {
        maven {
            name = "KotoolsCsvFile"
            url = uri(path = "https://maven.pkg.github.com/kotools/csv-file")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>(name = "gpr") { from(components["java"]) }
    }
}

kotlin.explicitApi = ExplicitApiMode.Strict
