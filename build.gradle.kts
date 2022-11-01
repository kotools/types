plugins {
    val kotlinVersion = "1.5.31"
    kotlin("multiplatform") version kotlinVersion apply false
    id("org.jetbrains.dokka") version kotlinVersion apply false
}

allprojects { repositories(RepositoryHandler::mavenCentral) }

subprojects { group = "io.github.kotools" }
