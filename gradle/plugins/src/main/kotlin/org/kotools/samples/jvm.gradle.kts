package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// ----------------------------- Script properties -----------------------------

private val projectSources: Directory = layout.projectDirectory.dir("src")
private val javaSamples: Directory = projectSources.dir("sample/java")

private val output: Provider<Directory> = layout.buildDirectory.dir("samples")
private val sourcesBackup: Provider<Directory> =
    output.map { it.dir("sources-backup") }

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinJvmProjectExtension = extensions.findByType()
    ?: error("Kotlin/JVM plugin wasn't applied to ${project}.")
private val kotlinMain: KotlinSourceSet = kotlin.sourceSets.getByName("main")
private val kotlinSample: KotlinSourceSet =
    kotlin.sourceSets.create("sample") {
        this.dependsOn(kotlinMain)
        this.kotlin.srcDir(javaSamples)
    }
kotlin.sourceSets.getByName("test")
    .dependsOn(kotlinSample)

private val java: JavaPluginExtension = extensions.getByType()
java.sourceSets.named("test") { this.java.srcDir(javaSamples) }

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
    this.outputDirectory = output.map { it.dir("extracted") }
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
    this.from(projectSources) { exclude("api", "sample", "test") }
    this.into(sourcesBackup)
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
    this.from(sourcesBackup)
    this.into(projectSources)
    this.outputs.upToDateWhen { false }
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

tasks.named("check").configure { this.dependsOn(checkSampleReferences) }

tasks.named("kotlinSourcesJar").configure {
    this.dependsOn(inlineSamples)
    this.finalizedBy(restoreMainSources)
}

private val kotlinCompilationTasks: TaskCollection<KotlinCompilationTask<*>> =
    tasks.withType(KotlinCompilationTask::class)
restoreMainSources.configure { this.mustRunAfter(kotlinCompilationTasks) }
