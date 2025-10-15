package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// ----------------------------- Script properties -----------------------------

private val projectSources: Directory = layout.projectDirectory.dir("src")
private val samplesBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("samples")
private val sourcesBackupBuildDirectory: Provider<Directory> =
    samplesBuildDirectory.map { it.dir("sources-backup") }

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.findByType()
    ?: error("Kotlin Multiplatform plugin wasn't applied to ${project}.")

// ----------------------------------- Tasks -----------------------------------

private val checkSampleSources: TaskProvider<CheckSampleSources>
        by tasks.registering(CheckSampleSources::class)
checkSampleSources.configure {
    this.description = "Checks the content of sample sources."
    this.sourceDirectory = projectSources
}

private val extractSamples: TaskProvider<ExtractSamples>
        by tasks.registering(ExtractSamples::class)
extractSamples.configure {
    this.description = "Extracts samples for KDoc."
    this.dependsOn(checkSampleSources)
    this.sourceDirectory = projectSources
    this.outputDirectory = samplesBuildDirectory.map { it.dir("extracted") }
}

private val checkSampleReferences: TaskProvider<CheckSampleReferences>
        by tasks.registering(CheckSampleReferences::class)
checkSampleReferences.configure {
    this.description = "Checks sample references from KDoc."
    this.sourceDirectory = projectSources
    this.extractedSamplesDirectory =
        extractSamples.flatMap(ExtractSamples::outputDirectory)
}

private val backupMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class)
backupMainSources.configure {
    this.description = "Copies main sources into the build directory."
    this.dependsOn(checkSampleReferences)
    this.from(projectSources) { exclude("api", "*Sample", "*Test") }
    this.into(sourcesBackupBuildDirectory)
}

private val inlineSamples: TaskProvider<InlineSamples>
        by tasks.registering(InlineSamples::class)
inlineSamples.configure {
    this.description = "Inlines KDoc samples."
    this.dependsOn(backupMainSources)
    this.sourceDirectory = projectSources
    this.extractedSamplesDirectory =
        extractSamples.flatMap(ExtractSamples::outputDirectory)
}

private val restoreMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class)
restoreMainSources.configure {
    this.description = "Restores main sources backup from the build directory."
    this.from(sourcesBackupBuildDirectory)
    this.into(projectSources)
}

// ----------------------------- Dokka integration -----------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    this.dependsOn(inlineSamples)
    this.finalizedBy(restoreMainSources)
}

rootProject.tasks.withType<DokkaMultiModuleTask>().configureEach {
    this.dependsOn(restoreMainSources)
}

// ---------------------------- Kotlin integration -----------------------------

tasks.named("check")
    .configure { this.dependsOn(checkSampleReferences) }

private val sourcesJarTaskNames: List<String> = kotlin.targets
    .map { if (it.name == "metadata") "sourcesJar" else "${it.name}SourcesJar" }
tasks.named { it in sourcesJarTaskNames }
    .configureEach {
        this.dependsOn(inlineSamples)
        this.finalizedBy(restoreMainSources)
    }

private val kotlinCompilationTasks: TaskCollection<KotlinCompilationTask<*>> =
    tasks.withType(KotlinCompilationTask::class)
restoreMainSources.configure { this.mustRunAfter(kotlinCompilationTasks) }
