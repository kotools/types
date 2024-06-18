package org.kotools.types

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

private val kotlin: KotlinMultiplatformExtension = extensions.findByType()
    ?: error("Kotlin Multiplatform plugin not found.")
private val mainSourceSetSuffix: String = "Main"
private val prefixes: Set<String> = kotlin.sourceSets.mapNotNull { it?.name }
    .filter { it.endsWith(mainSourceSetSuffix) }
    .map { it.substringBefore(mainSourceSetSuffix) }
    .toSet()
prefixes.forEach {
    val main: KotlinSourceSet =
        kotlin.sourceSets.getByName("$it$mainSourceSetSuffix")
    val test: KotlinSourceSet = kotlin.sourceSets.findByName("${it}Test")
        ?: return@forEach
    val samples: KotlinSourceSet =
        kotlin.sourceSets.create("${it}Samples") { dependsOn(main) }
    test.dependsOn(samples)
}
