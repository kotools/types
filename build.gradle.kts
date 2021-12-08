import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin(module = "jvm") version "1.5.32"
    `maven-publish`
}

group = "org.kotools"
version = "0.0.0"

repositories(RepositoryHandler::mavenCentral)

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
