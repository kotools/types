package org.kotools.types

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.kotools.types.gradle.tasks.ExtractKDocSamples

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.findByType()
    ?: error("Kotlin Multiplatform plugin not found.")
private val sampleSourceSets: List<KotlinSourceSet> = kotlin.sourceSets
    .mapNotNull { it?.name }
    .asSequence()
    .filter { it.endsWith("Main") }
    .map { it.substringBefore("Main") }
    .mapNotNull {
        val main: KotlinSourceSet = kotlin.sourceSets.getByName("${it}Main")
        val test: KotlinSourceSet = kotlin.sourceSets.findByName("${it}Test")
            ?: return@mapNotNull null
        kotlin.sourceSets.create("${it}Sample") { dependsOn(main) }
            .also(test::dependsOn)
    }
    .toList()

// ----------------------------------- Tasks -----------------------------------

private val samplesTaskGroup: String = "samples"

private val samplesBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("samples")

private val cleanSamples: TaskProvider<Delete>
        by tasks.registering(Delete::class)
cleanSamples.configure {
    description = "Deletes the 'samples' build directory."
    group = samplesTaskGroup
    setDelete(samplesBuildDirectory)
}

private val extractAllSamples: TaskProvider<Task> by tasks.registering
extractAllSamples.configure {
    description = "Extracts KDoc samples from all sample source sets."
    group = samplesTaskGroup
}
sampleSourceSets.forEach { sourceSet: KotlinSourceSet ->
    val sourceSetName: String = sourceSet.name
    val task: TaskProvider<ExtractKDocSamples> = sourceSetName
        .replaceFirstChar(Char::uppercaseChar)
        .let { tasks.register<ExtractKDocSamples>("extract${it}s") }
    task.configure {
        description = "Extracts KDoc samples from '$sourceSetName' source set."
        group = samplesTaskGroup
        onlyIf { sourceSet.kotlin.sourceDirectories.asFileTree.any() }
        sourceDirectories = sourceSet.kotlin.sourceDirectories
        outputDirectory =
            samplesBuildDirectory.map { it.dir("extracted/$sourceSetName") }
    }
    extractAllSamples.configure { dependsOn(task) }
}
