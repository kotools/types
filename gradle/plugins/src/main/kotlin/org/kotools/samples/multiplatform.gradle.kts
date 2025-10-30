package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask

// ----------------------------------- Tasks -----------------------------------

private val copySampleSources: TaskProvider<Copy> by tasks.registering(
    Copy::class
) {
    val source: Directory = layout.projectDirectory.dir("src")
    this.from(source) {
        this.include("**/*Sample.kt", "**/*Sample.java")
        this.exclude("api/", "*Main/")
    }
    val destination: Provider<Directory> =
        layout.buildDirectory.dir("kotools-samples/samples/sources")
    this.into(destination)
}

private val checkSampleSources: TaskProvider<CheckSampleSources> by tasks
    .registering(CheckSampleSources::class) {
        this.sourceDirectory = copySampleSources.map { it.destinationDir }
    }

private val extractSamples: TaskProvider<ExtractSamples> by tasks.registering(
    ExtractSamples::class
) {
    this.dependsOn(checkSampleSources)
    this.sourceDirectory = copySampleSources.map { it.destinationDir }
    this.outputDirectory = copySampleSources.map {
        it.destinationDir.resolve("../extracted")
    }
}

private val copyMainSources: TaskProvider<Copy> by tasks.registering(
    Copy::class
) {
    val source: Directory = layout.projectDirectory.dir("src")
    this.from(source) { exclude("api", "*Sample", "*Test") }
    val destination: Provider<Directory> =
        layout.buildDirectory.dir("kotools-samples/main/sources")
    this.into(destination)
}

private val checkSampleReferences: TaskProvider<CheckSampleReferences> by tasks
    .registering(CheckSampleReferences::class) {
        this.sourceDirectory = copyMainSources.map { it.destinationDir }
        this.extractedSamplesDirectory =
            extractSamples.flatMap(ExtractSamples::outputDirectory)
    }

private val inlineSamples: TaskProvider<InlineSamples> by tasks.registering(
    InlineSamples::class
) {
    this.sourceDirectory = copyMainSources.map { it.destinationDir }
    this.extractedSamplesDirectory =
        extractSamples.flatMap(ExtractSamples::outputDirectory)
    this.outputDirectory =
        copyMainSources.map { it.destinationDir.resolve("../inlined") }
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
