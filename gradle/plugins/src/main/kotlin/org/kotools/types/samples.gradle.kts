package org.kotools.types

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.kotools.types.samples.CheckNoInlinedSamples
import org.kotools.types.samples.CheckSamplesResolution
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

private val checkNoInlinedSamples: TaskProvider<CheckNoInlinedSamples> by
tasks.registering(CheckNoInlinedSamples::class) {
    description = "Checks that main sources don't have KDoc samples."
    group = samplesTaskGroup
    onlyIf { samples.failOnSamplesInKDoc }
    sources = projectSources
}

private val checkSamplesResolution: TaskProvider<CheckSamplesResolution> by
tasks.registering(CheckSamplesResolution::class) {
    description = "Checks that sample references from the main sources " +
            "target an existing file from the build directory."
    group = samplesTaskGroup
    samples = extractSamples.flatMap(ExtractCodeSamples::outputDirectory)
    sources = projectSources
}

private val checkSampleReferences: TaskProvider<Task> by tasks.registering {
    description = "Checks sample references from the main sources."
    group = samplesTaskGroup
    dependsOn(checkNoInlinedSamples, checkSamplesResolution)
}
tasks.named("check").configure { dependsOn += checkSampleReferences }

private val backupMainSources: TaskProvider<Copy> by
tasks.registering(Copy::class) {
    description = "Copies main sources into the build directory."
    group = samplesTaskGroup
    dependsOn += checkSampleReferences
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
checkNoInlinedSamples.configure {
    solutionTaskPath = restoreMainSources.get().path
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
kotlinCompilationTasks.configureEach { dependsOn += checkNoInlinedSamples }
restoreMainSources.configure { mustRunAfter(kotlinCompilationTasks) }
