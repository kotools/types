package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

/** Gradle convention plugin for configuring Kotlin Multiplatform projects. */
public class KotlinMultiplatformPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val plugins = PluginManager(project)
        plugins += PluginIdentifier.KotlinMultiplatform

        // ------------------------- Plugin extensions -------------------------

        val extensions = KotlinMultiplatformExtensionManager(project)
        extensions.configureKotlinMultiplatform()
        extensions.configurePublishing()

        // ------------------------------- Tasks -------------------------------

        val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
                by project.tasks.existing(Kotlin2JsCompile::class)
        val compileTestDevelopmentExecutableKotlinJs:
                TaskProvider<KotlinJsIrLink>
                by project.tasks.existing(KotlinJsIrLink::class)
        compileTestDevelopmentExecutableKotlinJs.configure {
            dependsOn(compileTestKotlinJs)
        }

        val jsNodeTest: TaskProvider<Task> by project.tasks.existing
        val checkJs: TaskProvider<Task> by project.tasks.registering
        checkJs.configure {
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsNodeTest)
        }

        val jvmTest: TaskProvider<Task> by project.tasks.existing
        val checkJvm: TaskProvider<Task> by project.tasks.registering
        checkJvm.configure {
            description = "Runs all checks for the Kotlin/JVM platform."
            dependsOn(jvmTest)
            if (PluginIdentifier.BinaryCompatibilityValidator in plugins) {
                val jvmApiCheck: TaskProvider<Task> by project.tasks.existing
                dependsOn(jvmApiCheck)
            }
        }

        project.tasks.withType<Jar>().configureEach {
            fun key(suffix: String): String = "Implementation-$suffix"
            val name: Pair<String, String> = key("Title") to project.name
            val version: Pair<String, Any> = key("Version") to project.version
            manifest.attributes(name, version)
        }
    }
}
