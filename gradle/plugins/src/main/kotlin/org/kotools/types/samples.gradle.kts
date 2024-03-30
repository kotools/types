package org.kotools.types

import org.jetbrains.dokka.gradle.DokkaTask
import org.kotools.types.samples.ExtractKotlinSamples
import org.kotools.types.samples.InlineSamples
import org.kotools.types.samples.SamplesExtension

private val samples: SamplesExtension = extensions.create("samples")

private val samplesOutput: Provider<Directory> =
    layout.buildDirectory.dir("samples")

// ------------------------- The `inlineSamples` task --------------------------

private val extractKotlinSamples: TaskProvider<ExtractKotlinSamples> by tasks
    .registering(ExtractKotlinSamples::class) {
        description = "Extract Kotlin code samples from sources."
        samples.source.dir("kotlin")
            .map { it.asFileTree }
            .let(this.sources::set)
        output.set(samplesOutput)
    }

private val backupMainSources: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Makes a copy of main sources in the build directory."
        from(layout.projectDirectory.dir("src")) { exclude("api", "*Test") }
        into(layout.buildDirectory.dir("srcBackup"))
    }

private val inlineSamples: TaskProvider<InlineSamples> by tasks
    .registering(InlineSamples::class) {
        description = "Inlines code samples in KDoc comments."
        listOf(backupMainSources).let(this::setDependsOn)
        sources.set(layout.projectDirectory.dir("src"))
        samples.set(extractKotlinSamples.flatMap(ExtractKotlinSamples::output))
    }

private val restoreMainSources: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description =
            "Restores the backup of main sources from the build directory."
        from(layout.buildDirectory.dir("srcBackup"))
        into(layout.projectDirectory.dir("src"))
    }

tasks.withType<DokkaTask>().configureEach {
    setDependsOn(listOf(inlineSamples))
    setFinalizedBy(listOf(restoreMainSources))
}

// -------------------------- The `cleanSamples` task --------------------------

private val cleanSamples by tasks.registering(Delete::class) {
    description = "Deletes extracted samples from the build directory."
    setDelete(samplesOutput)
}

tasks.named(LifecycleBasePlugin.CLEAN_TASK_NAME).configure {
    listOf(cleanSamples)
        .let(this::setDependsOn)
}
