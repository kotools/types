plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka)
    id("org.kotools.types.base")
    id("org.kotools.types.multi-module-api-reference")
}

allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}

tasks.register("cleanAll").configure {
    description = "Deletes all build directories."
    group = "recommended"
    allprojects.map { it.tasks.named("clean") }
        .let(this::setDependsOn)
}
