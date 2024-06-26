package org.kotools

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.kotools.samples.CheckSampleReferences
import org.kotools.samples.CheckSampleSources
import org.kotools.samples.ExtractSamples

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.findByType()
    ?: error("Kotlin Multiplatform plugin wasn't applied to ${this}.")

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
        val directory: Directory =
            layout.projectDirectory.dir("src/jvmSample/java")
        this.java.srcDir(directory)
    }
}

// ----------------------------------- Tasks -----------------------------------

private val projectSources: Directory = layout.projectDirectory.dir("src")

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
    this.outputDirectory = layout.buildDirectory.dir("samples/extracted")
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
    val destination: Provider<Directory> =
        layout.buildDirectory.dir("samples/sources-backup")
    this.into(destination)
}

