package kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.attributes
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
        project.extensions.getByType<KotlinMultiplatformExtension>()
            .configure()
        project.tasks.run {
            configureKotlinCompile()
            configureKotlinJvmTest()
            configureJar(project)
        }
    }
}

private fun KotlinMultiplatformExtension.configure() {
    explicitApi()
    js(IR) { browser() }
    jvm()
    jvmToolchain(17)
    linuxX64("linux")
    macosX64("macos")
    mingwX64("windows")
}

private fun PluginContainer.configureYarn(project: Project): Unit =
    withType<YarnPlugin>().configureEach {
        val yarn: YarnRootExtension = project.rootProject.extensions.getByType()
        yarn.lockFileDirectory = project.projectDir
    }

private fun TaskContainer.configureJar(project: Project): Unit = withType<Jar>()
    .configureEach {
        fun key(suffix: String): String = "Implementation-$suffix"
        val name: Pair<String, String> =
            key("Title") to project.rootProject.name
        val version: Pair<String, Any> = key("Version") to project.version
        manifest.attributes(name, version)
        archiveBaseName.set(project.rootProject.name)
    }

private fun TaskContainer.configureKotlinCompile(): Unit =
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            languageVersion = "1.5"
        }
    }

private fun TaskContainer.configureKotlinJvmTest(): Unit =
    withType<KotlinJvmTest>().configureEach { useJUnitPlatform() }
