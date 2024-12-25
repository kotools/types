package org.kotools.types.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

internal class KotlinMultiplatformTaskManager(private val project: Project) {
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
