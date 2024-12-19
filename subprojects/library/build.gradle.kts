import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.dev.tasks)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.kotools.types.documentation)
    alias(libs.plugins.kotools.samples.multiplatform)
    alias(libs.plugins.kotools.types.publication)
}

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

devTasks.list(tasks.apiDump, tasks.checkJs, tasks.checkJvm)

documentation {
    moduleName = "Kotools Types"
    packages = layout.projectDirectory.file("packages.md").asFile
}

kotlin.jvm(KotlinJvmTarget::withJava)
kotlin.sourceSets.configureEach {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

// -----------------------------------------------------------------------------

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)
}
