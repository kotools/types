package org.kotools.samples.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.kotools.samples.internal.KotlinJvmPluginNotFound
import java.util.Objects

/**
 * Gradle plugin responsible for integrating tested samples in the API
 * reference of Kotlin/JVM projects.
 *
 * @constructor Creates an instance of this plugin.
 */
public class KotoolsSamplesJvmPlugin : Plugin<Project> {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [KotoolsSamplesJvmPlugin], or returns `false` otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is KotoolsSamplesJvmPlugin

    /** Returns a hash code value for this plugin. */
    override fun hashCode(): Int = Objects.hash("$this")

    // ------------------------- Project configuration -------------------------

    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        this.kotlinSourceSets(project)
        this.javaSourceSets(project)
    }

    private fun kotlinSourceSets(project: Project) {
        val kotlin: KotlinJvmProjectExtension? = project.extensions.findByType()
        checkNotNull(kotlin) { KotlinJvmPluginNotFound(project) }
        val main: KotlinSourceSet = kotlin.sourceSets.getByName("main")
        val sample: KotlinSourceSet = kotlin.sourceSets.create("sample")
        val test: KotlinSourceSet = kotlin.sourceSets.getByName("test")
        sample.dependsOn(main)
        val javaSampleDirectory: Directory = this.javaSampleDirectory(project)
        sample.kotlin.srcDir(javaSampleDirectory)
        test.dependsOn(sample)
    }

    private fun javaSourceSets(project: Project) {
        val javaSampleDirectory: Directory = this.javaSampleDirectory(project)
        project.extensions.getByType<JavaPluginExtension>()
            .sourceSets
            .named("test")
            .configure { this.java.srcDir(javaSampleDirectory) }
    }

    private fun javaSampleDirectory(project: Project): Directory =
        project.layout.projectDirectory.dir("src/sample/java")

    // ------------------------------ Conversions ------------------------------

    /** Returns the string representation of this plugin. */
    override fun toString(): String =
        "Kotools Samples Gradle plugin for Kotlin/JVM projects"
}
