package org.kotools.types.gradle

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class KotlinJvmTaskManager(private val project: Project) {
    fun configureKotlinCompile() {
        val action: Action<KotlinCompile> = this.kotlinCompileAction()
        this.project.tasks.withType<KotlinCompile>()
            .configureEach(action)
    }

    private fun kotlinCompileAction(): Action<KotlinCompile> {
        val compilerOptionsAction: Action<KotlinJvmCompilerOptions> =
            this.kotlinJvmCompilerOptionsAction()
        return Action { this.compilerOptions(compilerOptionsAction) }
    }

    private fun kotlinJvmCompilerOptionsAction():
            Action<KotlinJvmCompilerOptions> = Action {
        this.allWarningsAsErrors.set(true)
        this.jvmTarget.set(JvmTarget.JVM_17)
    }

    fun configureTest(): Unit = this.project.tasks.withType<Test>()
        .configureEach(Test::useJUnitPlatform)
}
