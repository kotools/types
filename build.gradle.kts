plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka)
    id("org.kotools.types.base")
    id("org.kotools.types.documentation")
}

documentation.moduleName = "Kotools Types"

tasks.register("cleanAll").configure {
    description = "Deletes all build directories."
    group = "recommended"
    allprojects.map { it.tasks.named("clean") }
        .let(this::setDependsOn)
}

allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}
