package org.kotools

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

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
