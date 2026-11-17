package convention.kotlin

import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins { id("org.jetbrains.kotlin.multiplatform") }

// ----------------------------- Plugin extensions -----------------------------

private val kotlin: KotlinMultiplatformExtension = extensions.getByType()
kotlin.explicitApi()

kotlin.js(KotlinJsCompilerType.IR) {
    browser()
    nodejs()
    binaries.library()
}
rootProject.extensions.findByType<YarnRootExtension>()?.run {
    lockFileDirectory = rootProject.layout.projectDirectory.asFile
    resolution("braces", "3.0.3")
    resolution("follow-redirects", "1.15.6")
    resolution("rollup", "2.79.2")
}

kotlin.jvm {
    testRuns.configureEach {
        executionTask.configure(KotlinJvmTest::useJUnitPlatform)
    }
}

// Apple targets:
kotlin.iosX64()
kotlin.macosX64()
kotlin.iosArm64()
kotlin.iosSimulatorArm64()
kotlin.macosArm64()

// Linux targets:
kotlin.linuxX64()

// Windows targets:
kotlin.mingwX64()

kotlin.targets.configureEach {
    compilations.configureEach {
        compilerOptions.options.allWarningsAsErrors.set(true)
    }
}

kotlin.applyDefaultHierarchyTemplate()
kotlin.sourceSets {
    configureEach { languageSettings.optIn("kotlin.RequiresOptIn") }
    val commonTest: KotlinSourceSet by this.getting
    val jvmNativeTest: KotlinSourceSet by this.creating {
        this.dependsOn(commonTest)
    }
    this.getByName("jvmTest").dependsOn(jvmNativeTest)
    this.getByName("nativeTest").dependsOn(jvmNativeTest)
}

// ----------------------------------- Tasks -----------------------------------

private val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
        by tasks.existing(Kotlin2JsCompile::class)

tasks.named<KotlinJsIrLink>("compileTestDevelopmentExecutableKotlinJs")
    .configure { dependsOn(compileTestKotlinJs) }

tasks.withType<AbstractTestTask>().configureEach {
    this.testLogging.events(
        TestLogEvent.FAILED,
        TestLogEvent.SKIPPED,
        TestLogEvent.STANDARD_OUT
    )
}

tasks.withType<Jar>().configureEach {
    manifest.attributes(
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version.toString()
    )
}
