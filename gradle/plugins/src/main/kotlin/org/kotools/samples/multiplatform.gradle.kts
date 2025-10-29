package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// ----------------------------- Script properties -----------------------------

private val projectSources: Directory = layout.projectDirectory.dir("src")

private val samplesBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("kotools-samples")

private val sourcesBuildDirectory: Provider<Directory> =
    samplesBuildDirectory.map { it.dir("sources") }

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

private val cleanMainSourcesBackup: TaskProvider<Delete> by tasks
    .registering(Delete::class) { setDelete(sourcesBuildDirectory) }

private val backupMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class) {
            description = "Copies main sources into the build directory."
            dependsOn(checkSampleReferences, cleanMainSourcesBackup)
            from(projectSources) { exclude("api", "*Sample", "*Test") }
            into(sourcesBuildDirectory)
        }

private val inlineSamples: TaskProvider<InlineSamples>
        by tasks.registering(InlineSamples::class)
inlineSamples.configure {
    this.description = "Inlines KDoc samples."
    this.sourceDirectory = backupMainSources.map { it.destinationDir }
    this.extractedSamplesDirectory =
        extractSamples.flatMap(ExtractSamples::outputDirectory)
}

private val restoreMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class)
restoreMainSources.configure {
    this.description = "Restores main sources backup from the build directory."
    this.from(sourcesBuildDirectory)
    this.into(projectSources)
}

// ----------------------------- Dokka integration -----------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    this.dependsOn(inlineSamples)
    this.dokkaSourceSets
        .matching { it.name.endsWith("Main") && !it.sourceRoots.isEmpty }
        .configureEach {
            val sourceDirectory: Provider<Directory> =
                sourcesBuildDirectory.map { it.dir("${this.name}/kotlin") }
            this.sourceRoots.setFrom(sourceDirectory)
        }
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
