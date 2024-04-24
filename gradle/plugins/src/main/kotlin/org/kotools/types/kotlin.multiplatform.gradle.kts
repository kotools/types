package org.kotools.types

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.kotools.types.base")
    id("org.jetbrains.kotlin.multiplatform")
}

// -----------------------------------------------------------------------------
// ----------------------------- Plugin extensions -----------------------------
// -----------------------------------------------------------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.getByType()
kotlin.explicitApi()

// ------------------------------ Kotlin targets -------------------------------

kotlin.js(KotlinJsCompilerType.IR) {
    nodejs { testTask(KotlinJsTest::useMocha) }
    binaries.library()
}
plugins.withType<YarnPlugin>().configureEach {
    val yarn: YarnRootExtension = rootProject.extensions.getByType()
    yarn.lockFileDirectory = rootDir
    yarn.resolution("follow-redirects", "1.15.4")
    yarn.resolution("webpack", "5.76.3")
}

kotlin.jvm {
    compilations.configureEach {
        compilerOptions.configure { jvmTarget.set(JvmTarget.JVM_17) }
    }
    testRuns.configureEach {
        executionTask.configure(KotlinJvmTest::useJUnitPlatform)
    }
}

// Kotlin/Native: https://kotlinlang.org/docs/native-target-support.html
// Tier 1
kotlin.macosX64("macos")
kotlin.macosArm64()
// Tier 2
kotlin.linuxX64("linux")
// Tier 3
kotlin.mingwX64("windows")

kotlin.targets.configureEach {
    compilations.configureEach {
        compilerOptions.configure {
            allWarningsAsErrors.set(true)
            languageVersion.set(KotlinVersion.KOTLIN_1_5)
        }
    }
}

// ---------------------------- Kotlin source sets -----------------------------

kotlin.sourceSets {
    val commonMain: KotlinSourceSet by getting
    val jvmAndNativeMain: KotlinSourceSet by creating { dependsOn(commonMain) }
    val jvmMain: KotlinSourceSet by getting { dependsOn(jvmAndNativeMain) }
    val nativeMain: KotlinSourceSet by creating { dependsOn(jvmAndNativeMain) }
    val linuxMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val macosMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val macosArm64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val windowsMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
    configureEach { languageSettings.optIn("kotlin.RequiresOptIn") }
}

// -----------------------------------------------------------------------------
// ----------------------------------- Tasks -----------------------------------
// -----------------------------------------------------------------------------

private val compileTestKotlinJs: Kotlin2JsCompile by
tasks.getting(Kotlin2JsCompile::class)

private val compileTestDevelopmentExecutableKotlinJs: KotlinJsIrLink by
tasks.getting(KotlinJsIrLink::class) {
    dependsOn(compileTestKotlinJs)
}

tasks.withType<Jar>().configureEach {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

tasks.build.configure { group = "other" }
tasks.check.configure { group = "other" }

tasks {
    val tasks: TaskReportTask by getting(TaskReportTask::class)
    val jsNodeTest: KotlinJsTest by getting(KotlinJsTest::class)
    register("checkJs").configure {
        description = "Runs all checks for the Kotlin/JS platform."
        group = tasks.displayGroup
        dependsOn(jsNodeTest)
    }
    val jvmTest: KotlinJvmTest by getting(KotlinJvmTest::class)
    register("checkJvm").configure {
        description = "Runs all checks for the Kotlin/JVM platform."
        group = tasks.displayGroup
        dependsOn(jvmTest)
    }
}
