package org.kotools.samples

import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
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

private val platforms: Set<String> = kotlin.sourceSets.asSequence()
    .filterNotNull()
    .filter { it.name.endsWith("Main") }
    .map { it.name.substringBefore("Main") }
    .filter { platform: String ->
        kotlin.sourceSets.any { it?.name == "${platform}Test" }
    }
    .toSet()

platforms.forEach {
    val main: KotlinSourceSet = kotlin.sourceSets.getByName("${it}Main")
    val test: KotlinSourceSet = kotlin.sourceSets.getByName("${it}Test")
    val sample: KotlinSourceSet = kotlin.sourceSets.create("${it}Sample") {
        this.dependsOn(main)
    }
    test.dependsOn(sample)
}

afterEvaluate {
    val withJavaEnabled: Boolean = kotlin.targets.withType<KotlinJvmTarget>()
        .firstOrNull()
        ?.withJavaEnabled
        ?: false
    if (!withJavaEnabled) return@afterEvaluate
    val java: JavaPluginExtension = extensions.getByType()
    java.sourceSets.named("test").configure {
        val directory: Directory = projectSources.dir("jvmSample/java")
        this.java.srcDir(directory)
    }
}

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

platforms.map { it.replaceFirstChar(Char::uppercaseChar) }
    .mapNotNull { tasks.findByName("check$it") }
    .forEach { it.dependsOn(checkSampleReferences) }

kotlin.targets
    .map { if (it.name == "metadata") "sourcesJar" else "${it.name}SourcesJar" }
    .mapNotNull(tasks::findByName)
    .forEach {
        it.dependsOn(inlineSamples)
        it.finalizedBy(restoreMainSources)
    }

private val kotlinCompilationTasks: TaskCollection<KotlinCompilationTask<*>> =
    tasks.withType(KotlinCompilationTask::class)
restoreMainSources.configure { this.mustRunAfter(kotlinCompilationTasks) }
