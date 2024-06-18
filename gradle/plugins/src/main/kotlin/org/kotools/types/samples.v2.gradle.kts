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

private val extractAllSamples: TaskProvider<Task> by tasks.registering
extractAllSamples.configure {
    description = "Extract KDoc samples from all sample source sets."
    group = "samples"
}

sampleSourceSets.forEach { sourceSet: KotlinSourceSet ->
    val sourceSetName: String = sourceSet.name
    val task: TaskProvider<ExtractKDocSamples> = sourceSetName
        .replaceFirstChar(Char::uppercaseChar)
        .let { tasks.register<ExtractKDocSamples>("extract${it}s") }
    task.configure {
        description = "Extract KDoc samples from '$sourceSetName' source set."
        group = "samples"
        sourceDirectories = sourceSet.kotlin.sourceDirectories
        outputDirectory =
            layout.buildDirectory.dir("samples/extracted/$sourceSetName")
    }
    extractAllSamples.configure { dependsOn(task) }
}
