package convention.kotlin

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import kotlin.jvm.optionals.getOrNull

plugins { id("org.jetbrains.kotlin.multiplatform") }

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.getByType()
kotlin.explicitApi()

kotlin.js(KotlinJsCompilerType.IR) {
    nodejs()
    binaries.library()
}
rootProject.extensions.findByType<YarnRootExtension>()?.run {
    lockFileDirectory = rootProject.layout.projectDirectory.asFile
    resolution("braces", "3.0.3")
    resolution("follow-redirects", "1.15.6")
    resolution("rollup", "2.79.2")
    resolution("webpack", "5.94.0")
}

private val javaVersion: Int = extensions.getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion("java")
    .getOrNull()
    ?.requiredVersion
    ?.toInt()
    ?: error("Java version not found in version catalog")
kotlin.jvm {
    this.compilations.configureEach {
        this.compilerOptions.configure {
            this.jvmTarget = JvmTarget.fromTarget("$javaVersion")
            this.freeCompilerArgs.add("-Xjdk-release=$javaVersion")
        }
    }
    testRuns.configureEach {
        executionTask.configure(KotlinJvmTest::useJUnitPlatform)
    }
}
tasks.withType<JavaCompile>().configureEach {
    this.options.release = javaVersion
}

// Inspired from https://kotlinlang.org/docs/native-target-support.html.
// Tier 1
kotlin.macosArm64()
kotlin.iosSimulatorArm64()
// Tier 2
kotlin.linuxX64()
kotlin.macosX64()
kotlin.iosX64()
// Tier 3
kotlin.mingwX64()

kotlin.targets.configureEach {
    compilations.configureEach {
        compilerOptions.configure {
            allWarningsAsErrors = true
            languageVersion = KotlinVersion.KOTLIN_1_9
        }
    }
}

kotlin.applyDefaultHierarchyTemplate()
kotlin.sourceSets {
    configureEach { languageSettings.optIn("kotlin.RequiresOptIn") }
}

// ----------------------------------- Tasks -----------------------------------

private val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
        by tasks.existing(Kotlin2JsCompile::class)

tasks.named<KotlinJsIrLink>("compileTestDevelopmentExecutableKotlinJs")
    .configure { dependsOn(compileTestKotlinJs) }

tasks.withType<Jar>().configureEach {
    manifest.attributes(
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version.toString()
    )
}
