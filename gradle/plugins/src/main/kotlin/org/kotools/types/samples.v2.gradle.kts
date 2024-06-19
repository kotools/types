package org.kotools.types

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.kotools.types.gradle.tasks.CheckSampleSources
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

private val sourceSetsHavingFiles: List<KotlinSourceSet> =
    sampleSourceSets.filter { it.kotlin.sourceDirectories.asFileTree.any() }

private val checkPlatformSampleSources: List<TaskProvider<CheckSampleSources>> =
    sourceSetsHavingFiles.map { sourceSet: KotlinSourceSet ->
        val taskName: String = sourceSet.name
            .replaceFirstChar(Char::uppercaseChar)
            .let { "check${it}Sources" }
        tasks.register<CheckSampleSources>(taskName) {
            description = "Checks sources from '${sourceSet.name}' source set."
            group = samplesTaskGroup
            sourceDirectories = sourceSet.kotlin.sourceDirectories
        }
    }

private val checkAllSampleSources: TaskProvider<Task> by tasks.registering
checkAllSampleSources.configure {
    description = "Checks sources from all sample source sets."
    group = samplesTaskGroup
    dependsOn(checkPlatformSampleSources)
}

private val extractPlatformSamples: List<TaskProvider<ExtractKDocSamples>> =
    sourceSetsHavingFiles.map { sourceSet: KotlinSourceSet ->
        val sourceSetName: String = sourceSet.name
        val taskName: String = sourceSetName
            .replaceFirstChar(Char::uppercaseChar)
            .let { "extract${it}s" }
        tasks.register<ExtractKDocSamples>(taskName) {
            description =
                "Extracts KDoc samples from '$sourceSetName' source set."
            group = samplesTaskGroup
            sourceDirectories = sourceSet.kotlin.sourceDirectories
            outputDirectory =
                samplesBuildDirectory.map { it.dir("extracted/$sourceSetName") }
            checkPlatformSampleSources
                .filter { it.name.contains(sourceSetName, ignoreCase = true) }
                .let(this::setDependsOn)
        }
    }

private val extractAllSamples: TaskProvider<Task> by tasks.registering
extractAllSamples.configure {
    description = "Extracts KDoc samples from all sample source sets."
    group = samplesTaskGroup
    dependsOn(extractPlatformSamples)
}
