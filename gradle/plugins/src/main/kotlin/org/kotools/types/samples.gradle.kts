package org.kotools.types

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.kotools.types.samples.ExtractCodeSamples
import org.kotools.types.samples.InlineSamples
import org.kotools.types.samples.SamplesExtension

private val samples: SamplesExtension = extensions.create("samples")

private val samplesTaskGroup: String = "Samples"

private val samplesBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("samples")
private val sourcesBackupDirectory: Provider<Directory> =
    samplesBuildDirectory.map { it.dir("sources-backup") }

private val projectSources: Directory = layout.projectDirectory.dir("src")

// ------------------------------ Internal tasks -------------------------------

private val cleanSamples: TaskProvider<Delete> by
tasks.registering(Delete::class) {
    description = "Deletes the 'samples' build directory."
    group = samplesTaskGroup
    setDelete(samplesBuildDirectory)
}

private val extractSamples: TaskProvider<ExtractCodeSamples> by
tasks.registering(ExtractCodeSamples::class) {
    description = "Extract code samples from sources."
    group = samplesTaskGroup
    sourceDirectory =
        samples.project.map { it.layout.projectDirectory.dir("src/main") }
    outputDirectory = samplesBuildDirectory.map { it.dir("extracted") }
}

private val backupMainSources: TaskProvider<Copy> by
tasks.registering(Copy::class) {
    description = "Copies main sources into the build directory."
    group = samplesTaskGroup
    from(projectSources) { exclude("api", "*Test") }
    into(sourcesBackupDirectory)
}

private val inlineSamples: TaskProvider<InlineSamples> by
tasks.registering(InlineSamples::class) {
    description = "Inlines KDoc samples in project sources."
    group = samplesTaskGroup
    dependsOn += backupMainSources
    sourcesDirectory = projectSources
    samplesDirectory =
        extractSamples.flatMap(ExtractCodeSamples::outputDirectory)
}

private val restoreMainSources: TaskProvider<Copy> by
tasks.registering(Copy::class) {
    description = "Restores main sources backup from the build directory."
    group = samplesTaskGroup
    from(sourcesBackupDirectory)
    into(projectSources)
}

// ----------------------------- Dokka integration -----------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    dependsOn += inlineSamples
    finalizedBy(restoreMainSources)
}

rootProject.tasks.withType<DokkaMultiModuleTask>().configureEach {
    dependsOn += restoreMainSources
}

// ---------------------------- Kotlin integration -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.getByType()

kotlin.targets
    .map { if (it.name == "metadata") "sourcesJar" else "${it.name}SourcesJar" }
    .mapNotNull(tasks::findByName)
    .forEach {
        it.dependsOn += inlineSamples
        it.finalizedBy(restoreMainSources)
    }
private val kotlinCompilationTasks: TaskCollection<KotlinCompilationTask<*>> =
    tasks.withType(KotlinCompilationTask::class)
restoreMainSources.configure { mustRunAfter(kotlinCompilationTasks) }
