package org.kotools.types

import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.kotools.types.samples.ExtractCodeSamples
import org.kotools.types.samples.InlineSamples
import org.kotools.types.samples.SamplesExtension

private val samples: SamplesExtension = extensions.create("samples")

// ---------------------------- Samples extraction -----------------------------

private val samplesDirectory: Provider<Directory> =
    layout.buildDirectory.dir("samples")

private val extractKotlinSamples: TaskProvider<ExtractCodeSamples> by tasks
    .registering(ExtractCodeSamples::class) {
        description = "Extract Kotlin code samples from sources."
        sourceDirectory = samples.project.map {
            it.layout.projectDirectory.dir("src/main/kotlin")
        }
        outputDirectory = samplesDirectory
    }

private val extractJavaSamples: TaskProvider<ExtractCodeSamples> by tasks
    .registering(ExtractCodeSamples::class) {
        description = "Extract Java code samples from sources."
        sourceDirectory = samples.project.map {
            it.layout.projectDirectory.dir("src/main/java")
        }
        outputDirectory = samplesDirectory
    }

tasks.register<Delete>("cleanSamples").configure {
    description = "Deletes extracted samples from the build directory."
    setDelete(samplesDirectory)
}

// --------------------------- Main sources backup -----------------------------

private val srcDirectory: Directory = layout.projectDirectory.dir("src")

private val srcBackupDirectory: Provider<Directory> =
    layout.buildDirectory.dir("srcBackup")

private val backupMainSources: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Makes a copy of main sources in the build directory."
        from(srcDirectory) { exclude("api", "*Test") }
        into(srcBackupDirectory)
    }

private val restoreMainSources: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description =
            "Restores the backup of main sources from the build directory."
        from(srcBackupDirectory)
        into(srcDirectory)
    }

tasks.register<Delete>("cleanMainSourcesBackup").configure {
    description = "Deletes the backup of main sources from the build directory."
    setDelete(srcBackupDirectory)
}

// ----------------------------- Samples inlining ------------------------------

private val inlineSamples: TaskProvider<InlineSamples> by tasks
    .registering(InlineSamples::class) {
        description = "Inlines code samples in KDoc comments."
        setDependsOn(listOf(backupMainSources, extractJavaSamples))
        sourcesDirectory = srcDirectory
        samplesDirectory =
            extractKotlinSamples.flatMap(ExtractCodeSamples::outputDirectory)
    }

// ----------------------- External tasks configuration ------------------------

listOf(
    KotlinCompilationTask::class,
    DokkaTask::class,
    DokkaTaskPartial::class,
    Jar::class
).forEach {
    tasks.withType(it).configureEach {
        setDependsOn(listOf(inlineSamples))
        setFinalizedBy(listOf(restoreMainSources))
    }
}

tasks.named("jvmSourcesJar").configure {
    setDependsOn(listOf(inlineSamples))
    setFinalizedBy(listOf(restoreMainSources))
}

listOf("jvmApiCheck", "apiCheck", "apiDump").forEach {
    tasks.named(it).configure { setFinalizedBy(listOf(restoreMainSources)) }
}

restoreMainSources.configure {
    rootProject.tasks.withType<DokkaMultiModuleTask>()
        .let(this::setMustRunAfter)
}
