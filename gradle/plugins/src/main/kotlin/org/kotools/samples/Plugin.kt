package org.kotools.samples

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/** Gradle plugin responsible for inlining KDoc samples. */
public class KotoolsSamplesPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.shouldHaveKotlinMultiplatformPlugin()
        project.shouldHaveDokkaPlugin()
        val kotlin: KotlinMultiplatformExtension by project.extensions
        val platforms: Set<String> = kotlin.platforms()
        platforms.forEach { kotlin.sampleSourceSet(it) }
    }

    private fun Project.shouldHaveKotlinMultiplatformPlugin() {
        val isKotlinMultiplatformProject: Boolean = this.pluginManager
            .hasPlugin("org.jetbrains.kotlin.multiplatform")
        check(isKotlinMultiplatformProject) {
            "'${this.path}' doesn't have the Kotlin Multiplatform plugin."
        }
    }

    private fun Project.shouldHaveDokkaPlugin() {
        val projectHasDokka: Boolean =
            this.pluginManager.hasPlugin("org.jetbrains.dokka")
        check(projectHasDokka) {
            "'${this.path}' doesn't have the Dokka plugin."
        }
    }

    private fun KotlinMultiplatformExtension.platforms(): Set<String> = this
        .sourceSets
        .asSequence()
        .filterNotNull()
        .filter { it.name.endsWith("Main") }
        .map { it.name.substringBefore("Main") }
        .filter { platform: String ->
            this.sourceSets.any { it?.name == "${platform}Test" }
        }
        .toSet()

    private fun KotlinMultiplatformExtension.sampleSourceSet(platform: String) {
        val main: KotlinSourceSet = this.sourceSets.getByName("${platform}Main")
        val test: KotlinSourceSet = this.sourceSets.getByName("${platform}Test")
        val sample: KotlinSourceSet =
            this.sourceSets.create("${platform}Sample") { this.dependsOn(main) }
        test.dependsOn(sample)
    }
}
