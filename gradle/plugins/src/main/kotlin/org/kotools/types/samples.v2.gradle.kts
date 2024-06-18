package org.kotools.types

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

private val kotlin: KotlinMultiplatformExtension = extensions.findByType()
    ?: error("Kotlin Multiplatform plugin not found.")
kotlin.sourceSets {
    fun createSamples(platform: String) {
        val main: KotlinSourceSet = this.findByName("${platform}Main") ?: return
        val test: KotlinSourceSet = this.findByName("${platform}Test") ?: return
        val samples: KotlinSourceSet = this.create("${platform}Samples") {
            dependsOn(main)
        }
        test.dependsOn(samples)
    }
    this.asSequence()
        .filterNotNull()
        .map { it.name.substringBefore("Main") }
        .map { it.substringBefore("Test") }
        .toSet()
        .forEach(::createSamples)
}
