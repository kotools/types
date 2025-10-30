package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask

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
