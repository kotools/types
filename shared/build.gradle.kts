import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvm().compilations.all { kotlinOptions.jvmTarget = "1.8" }
    js(IR, KotlinJsTargetDsl::browser)
    linuxX64("linux")
    macosX64("macos")
    mingwX64("mingw")
    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain: KotlinSourceSet by getting {
            dependencies {
                implementation(project.dependencies.platform(kotlin("bom")))
            }
        }
    }
}
