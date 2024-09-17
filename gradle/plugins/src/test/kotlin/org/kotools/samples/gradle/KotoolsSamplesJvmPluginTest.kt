package org.kotools.samples.gradle

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.plugins.PluginApplicationException
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.kotools.samples.internal.KotlinJvmPluginNotFound
import java.util.Objects
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KotoolsSamplesJvmPluginTest {
    @Test
    fun `primary constructor should pass`() {
        KotoolsSamplesJvmPlugin()
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun `equals should pass with a KotoolsSamplesJvmPlugin type`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val other: Any = KotoolsSamplesJvmPlugin()
        val actual: Boolean = plugin == other
        val message = "Gradle plugins with the same type should be equal."
        assertTrue(actual, message)
    }

    @Test
    fun `equals should fail with another type than KotoolsSamplesJvmPlugin`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val other: Any = "$plugin"
        val actual: Boolean = plugin == other
        val message = "Gradle plugins with different types shouldn't be equal."
        assertFalse(actual, message)
    }

    @Test
    fun `hashCode should pass`() {
        val plugin = KotoolsSamplesJvmPlugin()
        val actual: Int = plugin.hashCode()
        val expected: Int = Objects.hash("$plugin")
        assertEquals(expected, actual)
    }

    // ------------------------- Project configuration -------------------------

    @Test
    fun `apply should fail if Kotlin JVM plugin wasn't applied to project`() {
        val project: Project = ProjectBuilder.builder()
            .build()
        val exception: PluginApplicationException = assertFailsWith {
            project.pluginManager.apply(KotoolsSamplesJvmPlugin::class)
        }
        val actual: String? = exception.cause?.message
        val expected: String = KotlinJvmPluginNotFound(project)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun `apply should create 'sample' Kotlin source set`() {
        val project: Project = this.validProject
        val kotlin: KotlinJvmProjectExtension = project.extensions.getByType()
        val sample: KotlinSourceSet? = kotlin.sourceSets.findByName("sample")
        val plugin = KotoolsSamplesJvmPlugin()
        assertNotNull(
            actual = sample,
            message = "$plugin should create 'sample' source set"
        )
        val sampleSources: Directory =
            project.layout.projectDirectory.dir("src/sample")
        val kotlinSamples: Directory = sampleSources.dir("kotlin")
        assertTrue(
            actual = kotlinSamples.asFile in sample.kotlin.sourceDirectories,
            message = "Kotlin sample directory should be included in $sample"
        )
        val javaSamples: Directory = sampleSources.dir("java")
        assertTrue(
            actual = javaSamples.asFile in sample.kotlin.sourceDirectories,
            message = "Java sample directory should be included in $sample"
        )
    }

    @Test
    fun `apply should configure 'main' Kotlin source set`() {
        val kotlin: KotlinJvmProjectExtension =
            this.validProject.extensions.getByType()
        val main: KotlinSourceSet = kotlin.sourceSets.getByName("main")
        val sample: KotlinSourceSet = kotlin.sourceSets.getByName("sample")
        val actual: Boolean = main in sample.dependsOn
        val message = "$sample should depend on $main"
        assertTrue(actual, message)
    }

    @Test
    fun `apply should configure 'test' Kotlin source set`() {
        val kotlin: KotlinJvmProjectExtension =
            this.validProject.extensions.getByType()
        val sample: KotlinSourceSet = kotlin.sourceSets.getByName("sample")
        val test: KotlinSourceSet = kotlin.sourceSets.getByName("test")
        val actual: Boolean = sample in test.dependsOn
        val message = "$test should depend on $sample"
        assertTrue(actual, message)
    }

    @Test
    fun `apply should configure 'test' Java source set`() {
        val project: Project = this.validProject
        val test: SourceSet = project.extensions
            .getByType<JavaPluginExtension>()
            .sourceSets
            .getByName("test")
        val sourceDirectories: FileCollection = test.java.sourceDirectories
        val javaSample: Directory =
            project.layout.projectDirectory.dir("src/sample/java")
        val actual: Boolean = javaSample.asFile in sourceDirectories
        val message = "Java sample directory should be included in ${test}."
        assertTrue(actual, message)
    }

    private val validProject: Project
        get() {
            val project: Project = ProjectBuilder.builder()
                .build()
            project.pluginManager.apply("org.jetbrains.kotlin.jvm")
            project.pluginManager.apply(KotoolsSamplesJvmPlugin::class)
            return project
        }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun `toString should pass`() {
        val actual: String = KotoolsSamplesJvmPlugin()
            .toString()
        val expected = "Kotools Samples Gradle plugin for Kotlin/JVM projects"
        assertEquals(expected, actual)
    }
}
