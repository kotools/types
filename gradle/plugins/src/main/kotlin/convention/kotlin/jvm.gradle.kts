package convention.kotlin

import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins { id("org.jetbrains.kotlin.jvm") }

private val kotlin: KotlinJvmProjectExtension = extensions.getByType()
kotlin.explicitApi()
kotlin.compilerOptions.allWarningsAsErrors.set(true)

tasks.named<Test>("test").configure(Test::useJUnitPlatform)
