package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// ----------------------------- Script properties -----------------------------

private val projectSources: Directory = layout.projectDirectory.dir("src")

private val buildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("kotools-samples")

// ----------------------------------- Tasks -----------------------------------

private val checkSampleSources: TaskProvider<CheckSampleSources> by tasks
    .registering(CheckSampleSources::class) {
        this.sourceDirectory = projectSources
    }

private val extractSamples: TaskProvider<ExtractSamples> by tasks.registering(
    ExtractSamples::class
) {
    this.dependsOn(checkSampleSources)
    this.sourceDirectory = projectSources
    this.outputDirectory = buildDirectory.map { it.dir("extracted") }
}

private val checkSampleReferences: TaskProvider<CheckSampleReferences> by tasks
    .registering(CheckSampleReferences::class) {
        this.sourceDirectory = projectSources
        this.extractedSamplesDirectory =
            extractSamples.flatMap(ExtractSamples::outputDirectory)
    }

private val inlineSamples: TaskProvider<InlineSamples> by tasks.registering(
    InlineSamples::class
) {
    this.dependsOn(checkSampleReferences)
    this.sourceDirectory = projectSources
    this.extractedSamplesDirectory =
        extractSamples.flatMap(ExtractSamples::outputDirectory)
    this.outputDirectory = buildDirectory.map { it.dir("inlined") }
}

// -------------------------- Base plugin integration --------------------------

tasks.named { it == "check" }
    .configureEach { this.dependsOn(checkSampleReferences) }

// ----------------------------- Dokka integration -----------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    this.dokkaSourceSets
        .matching { it.name.endsWith("Main") && !it.sourceRoots.isEmpty }
        .configureEach {
            val source: Provider<Directory> =
                inlineSamples.flatMap(InlineSamples::outputDirectory)
            this.sourceRoots.setFrom(source)
        }
}

// --------------------- Kotlin Multiplatform integration ----------------------

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    val kotlin: KotlinMultiplatformExtension = extensions.getByType()
    tasks.withType<KotlinCompilationTask<*>>()
        .configureEach { this.mustRunAfter(inlineSamples) }
    tasks.withType<Jar>()
        .named { it.endsWith("sourcesJar", ignoreCase = true) }
        .configureEach {
            this.dependsOn(inlineSamples)
            this.doFirst {
                kotlin.sourceSets.named { it.endsWith("Main") }
                    .configureEach {
                        val inlinedMainSources: Provider<Directory> = layout
                            .buildDirectory
                            .dir("kotools-samples/inlined/${this.name}/kotlin")
                        this.kotlin.setSrcDirs(listOf(inlinedMainSources))
                    }
            }
        }
}
