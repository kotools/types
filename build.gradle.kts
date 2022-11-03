plugins {
    val kotlinVersion = "1.5.31"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("org.jetbrains.dokka") version kotlinVersion
}

allprojects { repositories(RepositoryHandler::mavenCentral) }

subprojects { group = "io.github.kotools" }

// ---------- Documentation generation ----------

tasks {
    val dokkaDirectory: File = buildDir.resolve("dokka")
    dokkaHtmlMultiModule { outputDirectory.set(dokkaDirectory) }
    register<Delete>("cleanDokkaHtmlMultiModule") { delete(dokkaDirectory) }
}
