package org.kotools.types.gradle

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class KotlinJvmTaskManager(private val project: Project) {
    fun kotlinCompile() {
        val action: Action<KotlinJvmCompilerOptions> =
            this.kotlinJvmCompilerOptionsAction()
        this.project.tasks.withType<KotlinCompile>()
            .configureEach { compilerOptions(action) }
    }

    private fun kotlinJvmCompilerOptionsAction():
            Action<KotlinJvmCompilerOptions> = Action {
        this.allWarningsAsErrors.set(true)
        this.jvmTarget.set(JvmTarget.JVM_17)
    }

    fun test(): Unit = this.project.tasks.withType<Test>()
        .configureEach(Test::useJUnitPlatform)
}
