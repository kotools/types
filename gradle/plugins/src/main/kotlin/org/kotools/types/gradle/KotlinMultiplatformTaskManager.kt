package org.kotools.types.gradle

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

internal class KotlinMultiplatformTaskManager(private val project: Project) {
    fun checkJs() {
        val jsNodeTest: TaskProvider<Task> by project.tasks.existing
        val checkJs: TaskProvider<Task> by project.tasks.registering
        checkJs.configure {
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsNodeTest)
        }
    }

    fun checkJvm(plugins: PluginManager) {
        val jvmTest: TaskProvider<Task> by project.tasks.existing
        val checkJvm: TaskProvider<Task> by project.tasks.registering
        checkJvm.configure {
            description = "Runs all checks for the Kotlin/JVM platform."
            dependsOn(jvmTest)
            dependsOnJvmApiCheck(plugins)
        }
    }

    fun compileTestDevelopmentExecutableKotlinJs() {
        val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
                by project.tasks.existing(Kotlin2JsCompile::class)
        val compileTestDevelopmentExecutableKotlinJs:
                TaskProvider<KotlinJsIrLink>
                by project.tasks.existing(KotlinJsIrLink::class)
        compileTestDevelopmentExecutableKotlinJs.configure {
            dependsOn(compileTestKotlinJs)
        }
    }

    fun jars(): Unit = project.tasks.withType<Jar>().configureEach {
        manifest.attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version.toString()
        )
    }
}

private fun Task.dependsOnJvmApiCheck(plugins: PluginManager) {
    if (PluginIdentifier.BinaryCompatibilityValidator !in plugins) return
    val jvmApiCheck: TaskProvider<Task> by project.tasks.existing
    dependsOn(jvmApiCheck)
}
