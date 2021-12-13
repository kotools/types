import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin(module = "jvm") version Kotlin.version
    `maven-publish`
}

group = "io.github.kotools"
version = "1.0.0-SNAPSHOT"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    setOf(Kotlin.coroutines, Library.csv)
        .forEach(::implementation)
    testImplementation(kotlin(module = "test"))
    testRuntimeOnly(Library.slf4j)
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri(path = "https://maven.pkg.github.com/kotools/csv")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            url = uri(
                path = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        register<MavenPublication>(name = "gpr") { from(components["java"]) }
    }
}

kotlin.explicitApi = ExplicitApiMode.Strict
