package org.kotools.types

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.kotools.types.samples.ExtractKotlinSamples

private val samplesOutput: Provider<Directory> =
    layout.buildDirectory.dir("samples")

// --------------------- The `extractKotlinSamples` task -----------------------

private val extractKotlinSamples by tasks
    .registering(ExtractKotlinSamples::class) {
        description = "Extract Kotlin code samples from sources."
        tasks.named<KotlinCompile>("compileKotlin")
            .map { it.sources.asFileTree }
            .let(this.sources::set)
        output.set(samplesOutput)
    }

tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure {
    listOf(extractKotlinSamples)
        .let(this::setDependsOn)
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
