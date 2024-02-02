import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
    id("kotools.types.publication")
}

group = "org.kotools"

repositories.mavenCentral()

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(projects.typesInternal)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

tasks.register("unit")

val optInInternals = "-opt-in=kotools.types.internal.InternalKotoolsTypesApi"
tasks.withType<KotlinCompileCommon>().configureEach {
    compilerOptions.freeCompilerArgs.add(optInInternals)
}
tasks.withType<Kotlin2JsCompile>().configureEach {
    compilerOptions.freeCompilerArgs.add(optInInternals)
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.freeCompilerArgs.add(optInInternals)
}
tasks.withType<KotlinNativeCompile>().configureEach {
    compilerOptions.freeCompilerArgs.add(optInInternals)
}

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("Kotools Types")
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        includes.setFrom("dokka/packages.md")
        reportUndocumented.set(true)
    }
}
