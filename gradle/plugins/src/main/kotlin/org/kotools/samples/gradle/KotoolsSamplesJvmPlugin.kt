package org.kotools.samples.gradle

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.kotlin.dsl.findByType
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
    override fun apply(project: Project): Unit =
        project.configureKotlinSourceSets()

    private fun Project.configureKotlinSourceSets() {
        val kotlin: KotlinJvmProjectExtension? = this.extensions.findByType()
        checkNotNull(kotlin) { KotlinJvmPluginNotFound(this) }
        val main: KotlinSourceSet = kotlin.sourceSets.getByName("main")
        val sample: KotlinSourceSet = kotlin.sourceSets.sample(this)
        sample.dependsOn(main)
        kotlin.sourceSets.getByName("test")
            .dependsOn(sample)
    }

    private fun NamedDomainObjectContainer<KotlinSourceSet>.sample(
        project: Project
    ): KotlinSourceSet = this.create("sample") {
        val javaSamples: Directory =
            project.layout.projectDirectory.dir("src/sample/java")
        this.kotlin.srcDir(javaSamples)
    }

    // ------------------------------ Conversions ------------------------------

    /** Returns the string representation of this plugin. */
    override fun toString(): String =
        "Kotools Samples Gradle plugin for Kotlin/JVM projects"
}
