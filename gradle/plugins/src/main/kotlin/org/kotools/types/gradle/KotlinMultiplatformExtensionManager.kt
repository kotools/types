package org.kotools.types.gradle

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

internal class KotlinMultiplatformExtensionManager(
    private val project: Project
) {
    fun configureKotlinMultiplatform() {
        val kotlin: KotlinMultiplatformExtension =
            this.project.extensions.getByType()
        kotlin.explicitApi()

        kotlin.js(KotlinJsCompilerType.IR) {
            this.nodejs { this.testTask(KotlinJsTest::useMocha) }
            this.binaries.library()
        }
        val rootProjectPlugins = PluginManager(this.project.rootProject)
        if (YarnPlugin::class in rootProjectPlugins) {
            val yarn: YarnRootExtension =
                this.project.rootProject.extensions.getByType()
            yarn.lockFileDirectory =
                this.project.rootProject.layout.projectDirectory.asFile
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

        kotlin.sourceSets.run {
            val commonMain: KotlinSourceSet by getting
            val jvmAndNativeMain: KotlinSourceSet by creating
            jvmAndNativeMain.dependsOn(commonMain)
            val jvmMain: KotlinSourceSet by getting
            jvmMain.dependsOn(jvmAndNativeMain)
            val nativeMain: KotlinSourceSet by creating
            nativeMain.dependsOn(jvmAndNativeMain)
            val linuxMain: KotlinSourceSet by getting
            linuxMain.dependsOn(nativeMain)
            val macosMain: KotlinSourceSet by getting
            macosMain.dependsOn(nativeMain)
            val macosArm64Main: KotlinSourceSet by getting
            macosArm64Main.dependsOn(nativeMain)
            val windowsMain: KotlinSourceSet by getting
            windowsMain.dependsOn(nativeMain)
            configureEach { languageSettings.optIn("kotlin.RequiresOptIn") }
        }
    }

    fun configurePublishing() {
        val publishing: PublishingExtension =
            this.project.extensions.findByType() ?: return
        publishing.publications.named<MavenPublication>("kotlinMultiplatform")
            .configure {
                groupId = "${project.group}"
                artifactId = project.name
                version = "${project.version}"
            }
    }
}
