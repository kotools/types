package kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/** Plugin configuring the Kotlin Multiplatform plugin. */
public class MultiplatformPlugin : Plugin<Project> {
    /** Applies this plugin to the given [project]. */
    override fun apply(project: Project) {
        project.rootProject.plugins.configureYarn(project)
        project.extensions.configure()
        project.tasks.configure(project)
    }
}

private fun PluginContainer.configureYarn(project: Project): Unit =
    withType<YarnPlugin>().configureEach {
        val yarn: YarnRootExtension = project.rootProject.extensions.getByType()
        yarn.lockFileDirectory = project.rootDir
        yarn.resolution("follow-redirects", "1.15.4")
        yarn.resolution("webpack", "5.76.3")
    }

private fun ExtensionContainer.configure() {
    val kotlin: KotlinMultiplatformExtension = getByType()
    kotlin.explicitApi()
    kotlin.js(KotlinJsCompilerType.IR) {
        nodejs()
        binaries.library()
    }
    kotlin.jvm()
    kotlin.nativeTargets()
    kotlin.sourceSets.configureEach {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
    kotlin.targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                allWarningsAsErrors.set(true)
                languageVersion.set(KotlinVersion.KOTLIN_1_5)
            }
        }
    }
}

private fun KotlinMultiplatformExtension.nativeTargets() {
    // According to https://kotlinlang.org/docs/native-target-support.html
    // Tier 1
    macosX64("macos")
    macosArm64()
    // Tier 2
    linuxX64("linux")
    // Tier 3
    mingwX64("windows")
}

private fun TaskContainer.configure(project: Project) {
    withType<KotlinCompile>().configureEach {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }
    withType<KotlinJvmTest>()
        .configureEach(Test::useJUnitPlatform)
    withType<Jar>().configureEach {
        fun key(suffix: String): String = "Implementation-$suffix"
        val name: Pair<String, String> = key("Title") to project.name
        val version: Pair<String, Any> = key("Version") to project.version
        manifest.attributes(name, version)
        archiveBaseName.set(project.rootProject.name)
    }
}
