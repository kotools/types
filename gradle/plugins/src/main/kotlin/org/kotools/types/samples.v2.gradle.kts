package org.kotools.types

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.kotools.types.gradle.tasks.CheckSampleResolutions
import org.kotools.types.gradle.tasks.CheckSampleSources
import org.kotools.types.gradle.tasks.ExtractKDocSamples
import org.kotools.types.gradle.tasks.InlineKDocSamples

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

private val sampleSourceSetsHavingFiles: List<KotlinSourceSet> =
    sampleSourceSets.filter { it.kotlin.sourceDirectories.asFileTree.any() }

private val checkPlatformSampleSources: List<TaskProvider<CheckSampleSources>> =
    sampleSourceSetsHavingFiles.map { sourceSet: KotlinSourceSet ->
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
    sampleSourceSetsHavingFiles.map { sourceSet: KotlinSourceSet ->
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

private val checkPlatformSampleResolutions:
        List<TaskProvider<CheckSampleResolutions>> = extractPlatformSamples
    .map { task: TaskProvider<ExtractKDocSamples> ->
        val platform: String = task.name.substringAfter("extract")
            .substringBefore("Samples")
        val name = "check${platform}SampleResolutions"
        val sourceSet: KotlinSourceSet = kotlin.sourceSets.asSequence()
            .filterNotNull()
            .filter { it.name.contains(platform, ignoreCase = true) }
            .first { it.name.endsWith("Main") }
        tasks.register<CheckSampleResolutions>(name) {
            description =
                "Checks sample resolutions from '${sourceSet.name}' source set."
            group = samplesTaskGroup
            sourceDirectories = sourceSet.kotlin.sourceDirectories
            extractedSamplesDirectory =
                task.flatMap(ExtractKDocSamples::outputDirectory)
        }
    }

private val checkAllSampleResolutions: TaskProvider<Task> by tasks.registering
checkAllSampleResolutions.configure {
    description = "Checks sample resolutions from all source sets."
    group = samplesTaskGroup
    dependsOn(checkPlatformSampleResolutions)
}

private val projectSources: Directory = layout.projectDirectory.dir("src")
private val sourcesBackupDirectory: Provider<Directory> =
    samplesBuildDirectory.map { it.dir("sources-backup") }

private val backupMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class)
backupMainSources.configure {
    description = "Copies main sources into the build directory."
    group = samplesTaskGroup
    dependsOn += checkAllSampleResolutions
    from(projectSources) { exclude("api", "*Sample", "*Test") }
    into(sourcesBackupDirectory)
}

private val restoreMainSources: TaskProvider<Copy>
        by tasks.registering(Copy::class)
restoreMainSources.configure {
    description = "Restores main sources backup from the build directory."
    group = samplesTaskGroup
    from(sourcesBackupDirectory)
    into(projectSources)
}

private val inlinePlatformSamples: List<TaskProvider<InlineKDocSamples>> =
    extractPlatformSamples.map { task: TaskProvider<ExtractKDocSamples> ->
        val platform: String = task.name.substringAfter("extract")
            .substringBefore("Samples")
        val name = "inline${platform}Samples"
        val sourceSet: KotlinSourceSet = kotlin.sourceSets.asSequence()
            .filterNotNull()
            .filter { it.name.contains(platform, ignoreCase = true) }
            .first { it.name.endsWith("Main") }
        tasks.register<InlineKDocSamples>(name) {
            description =
                "Inlines KDoc samples from '${sourceSet.name}' source set."
            group = samplesTaskGroup
            dependsOn(backupMainSources)
            sourceDirectories = sourceSet.kotlin.sourceDirectories
            extractedSamplesDirectory =
                task.flatMap(ExtractKDocSamples::outputDirectory)
        }
    }

private val inlineAllSamples: TaskProvider<Task> by tasks.registering
inlineAllSamples.configure {
    description = "Inlines KDoc samples from all source sets."
    group = samplesTaskGroup
    dependsOn(inlinePlatformSamples)
}

// ----------------------------- Dokka integration -----------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    dependsOn += inlineAllSamples
    finalizedBy(restoreMainSources)
}

rootProject.tasks.withType<DokkaMultiModuleTask>().configureEach {
    dependsOn += restoreMainSources
}

// ---------------------------- Kotlin integration -----------------------------

kotlin.targets
    .map { if (it.name == "metadata") "sourcesJar" else "${it.name}SourcesJar" }
    .mapNotNull(tasks::findByName)
    .forEach {
        it.dependsOn += inlineAllSamples
        it.finalizedBy(restoreMainSources)
    }

private val kotlinCompilationTasks: TaskCollection<KotlinCompilationTask<*>> =
    tasks.withType(KotlinCompilationTask::class)
restoreMainSources.configure { mustRunAfter(kotlinCompilationTasks) }
