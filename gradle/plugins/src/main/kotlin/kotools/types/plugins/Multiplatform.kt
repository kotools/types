/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.plugins

import kotools.types.KotoolsTypesPackage
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/** Plugin configuring the Kotlin Multiplatform plugin. */
public class MultiplatformPlugin : Plugin<Project> {
    /** Applies this plugin to the given [project]. */
    override fun apply(project: Project) {
        project.rootProject.plugins.configureYarn(project)
        project.extensions.configure(project)
        val kotlinCompile: KotlinCompileExtension =
            project.extensions.create("kotlinCompile")
        project.tasks.configure(project, kotlinCompile)
    }
}

private fun PluginContainer.configureYarn(project: Project): Unit =
    withType<YarnPlugin>().configureEach {
        val yarn: YarnRootExtension = project.rootProject.extensions.getByType()
        yarn.lockFileDirectory = project.rootDir
        project.property("webpack.version")
            ?.let { yarn.resolution("webpack", "$it") }
            ?: error("The 'webpack.version' property wasn't found.")
    }

private fun ExtensionContainer.configure(project: Project) {
    val kotlin: KotlinMultiplatformExtension = getByType()
    kotlin.run {
        explicitApi()
        js(IR) { browser() }
        jvm()
        project.property("java.version")
            ?.toString()
            ?.toIntOrNull()
            ?.let(::jvmToolchain)
            ?: error("The 'java.version' property wasn't found.")
        linuxX64("linux")
        macosX64("macos")
        mingwX64("windows")
    }
}

/** Extension for configuring the [KotlinCompile] task from a build script. */
public interface KotlinCompileExtension {
    /** Property for configuring [KotlinCompile.javaPackagePrefix]. */
    public val javaPackagePrefix: Property<KotoolsTypesPackage>
}

private fun TaskContainer.configure(
    project: Project,
    kotlinCompileExtension: KotlinCompileExtension
) {
    withType<KotlinCompile>().configureEach {
        kotlinCompileExtension.javaPackagePrefix.orNull?.let {
            javaPackagePrefix = "$it"
        }
        kotlinOptions {
            allWarningsAsErrors = true
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            project.property("kotlin.language.version")
                ?.let { languageVersion = "$it" }
        }
    }
    withType<KotlinJvmTest>().configureEach { useJUnitPlatform() }
    withType<Jar>().configureEach {
        fun key(suffix: String): String = "Implementation-$suffix"
        val name: Pair<String, String> = key("Title") to project.name
        val version: Pair<String, Any> = key("Version") to project.version
        manifest.attributes(name, version)
        archiveBaseName.set(project.rootProject.name)
    }
}
