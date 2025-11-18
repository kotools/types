package org.kotools.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * Gradle plugin that configures the compatibility of Java and Kotlin projects.
 */
public class CompatibilityPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val compatibility = CompatibilityExtension(project)
        project.tasks.javaCompile(compatibility)
        project.withKotlinMultiplatform(compatibility)
    }
}

// ----------------------------------- Java ------------------------------------

private fun TaskContainer.javaCompile(
    compatibility: CompatibilityExtension
): Unit = this.withType<JavaCompile>().configureEach {
    val version: Provider<Int> = compatibility.java.map(String::toInt)
    this.options.release.set(version)
}

// --------------------------- Kotlin Multiplatform ----------------------------

private fun Project.withKotlinMultiplatform(
    compatibility: CompatibilityExtension
): Unit = this.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    val kotlin: KotlinMultiplatformExtension = project.extensions.getByType()
    kotlin.configureAllTargets(compatibility)
    kotlin.configureJvmTargets(compatibility)
    compatibility.kotlin.map { kotlin.coreLibrariesVersion = it }
}

private fun KotlinMultiplatformExtension.configureAllTargets(
    compatibility: CompatibilityExtension
): Unit = this.targets.configureEach {
    this.compilations.configureEach {
        val version: Provider<KotlinVersion> = compatibility.kotlin
            .map { it.substringBeforeLast('.') }
            .map(KotlinVersion.Companion::fromVersion)
        this.compilerOptions.options.apiVersion.set(version)
        this.compilerOptions.options.languageVersion.set(version)
    }
}

private fun KotlinMultiplatformExtension.configureJvmTargets(
    compatibility: CompatibilityExtension
): Unit = this.targets.withType<KotlinJvmTarget>().configureEach {
    this.compilations.configureEach {
        val javaVersion: Property<String> = compatibility.java
        val jvmTarget: Provider<JvmTarget> =
            javaVersion.map(JvmTarget.Companion::fromTarget)
        this.compilerOptions.options.jvmTarget.set(jvmTarget)
        javaVersion.map {
            val release = "-Xjdk-release=$it"
            this.compilerOptions.options.freeCompilerArgs.add(release)
        }
    }
}
