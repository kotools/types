package org.kotools.types.gradle

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

plugins { kotlin("multiplatform") }

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.getByType()
kotlin.explicitApi()

kotlin.js(KotlinJsCompilerType.IR) {
    nodejs { testTask(KotlinJsTest::useMocha) }
    binaries.library()
}
rootProject.plugins.withType<YarnPlugin>().configureEach {
    val yarn: YarnRootExtension = rootProject.extensions.getByType()
    yarn.lockFileDirectory = rootProject.layout.projectDirectory.asFile
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

// Kotlin Native: https://kotlinlang.org/docs/native-target-support.html
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

extensions.findByType<PublishingExtension>()?.let {
    it.publications.named<MavenPublication>("kotlinMultiplatform").configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }
}

// ----------------------------------- Tasks -----------------------------------

private val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
        by tasks.existing(Kotlin2JsCompile::class)

private val compileTestDevelopmentExecutableKotlinJs:
        TaskProvider<KotlinJsIrLink>
        by tasks.existing(KotlinJsIrLink::class)
compileTestDevelopmentExecutableKotlinJs.configure {
    dependsOn(compileTestKotlinJs)
}

private val jsNodeTest: TaskProvider<Task> by tasks.existing

private val checkJs: TaskProvider<Task> by tasks.registering
checkJs.configure {
    description = "Runs all checks for the Kotlin/JS platform."
    dependsOn(jsNodeTest)
}

private val jvmTest: TaskProvider<Task> by tasks.existing

private val checkJvm: TaskProvider<Task> by tasks.registering
checkJvm.configure {
    description = "Runs all checks for the Kotlin/JVM platform."
    dependsOn(jvmTest)
    val hasKotlinBinaryCompatibilityValidatorPlugin: Boolean = pluginManager
        .hasPlugin("org.jetbrains.kotlinx.binary-compatibility-validator")
    if (hasKotlinBinaryCompatibilityValidatorPlugin) {
        val jvmApiCheck: TaskProvider<Task> by tasks.existing
        dependsOn(jvmApiCheck)
    }
}

tasks.withType<Jar>().configureEach {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}
