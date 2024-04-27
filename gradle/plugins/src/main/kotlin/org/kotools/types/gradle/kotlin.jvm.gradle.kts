package org.kotools.types.gradle

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { kotlin("jvm") }

private val kotlin: KotlinJvmProjectExtension = extensions.getByType()
kotlin.explicitApi()

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        allWarningsAsErrors.set(true)
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

private val test: TaskProvider<Test> by tasks.existing(Test::class)
test.configure(Test::useJUnitPlatform)
