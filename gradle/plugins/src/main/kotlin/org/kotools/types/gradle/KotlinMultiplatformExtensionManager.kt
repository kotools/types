package org.kotools.types.gradle

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.provideDelegate
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
    fun kotlinMultiplatform() {
        val kotlin: KotlinMultiplatformExtension =
            this.project.extensions.getByType()
        kotlin.explicitApi()
        this.configureKotlinJsTarget(kotlin)
        this.configureKotlinJvmTarget(kotlin)
        this.configureKotlinNativeTargets(kotlin)
        this.configureAllKotlinTargets(kotlin)
        this.configureKotlinSourceSets(kotlin)
    }

    private fun configureKotlinJsTarget(kotlin: KotlinMultiplatformExtension) {
        kotlin.js(KotlinJsCompilerType.IR) {
            this.nodejs {
                val useMocha: Action<KotlinJsTest> = Action { this.useMocha() }
                this.testTask(useMocha)
            }
            this.binaries.library()
        }
        val rootProjectPlugins = PluginManager(this.project.rootProject)
        if (YarnPlugin::class in rootProjectPlugins) this.configureYarnRoot()
    }

    private fun configureYarnRoot() {
        val yarn: YarnRootExtension =
            this.project.rootProject.extensions.getByType()
        yarn.lockFileDirectory =
            this.project.rootProject.layout.projectDirectory.asFile
        yarn.resolution("braces", "3.0.3")
        yarn.resolution("follow-redirects", "1.15.6")
        yarn.resolution("rollup", "2.79.2")
        yarn.resolution("webpack", "5.94.0")
    }

    private fun configureKotlinJvmTarget(kotlin: KotlinMultiplatformExtension) {
        kotlin.jvm {
            this.testRuns.configureEach {
                this.executionTask.configure(KotlinJvmTest::useJUnitPlatform)
            }
        }
        kotlin.jvmToolchain(17)
    }

    private fun configureKotlinNativeTargets(
        kotlin: KotlinMultiplatformExtension
    ) {
        // Inspired from https://kotlinlang.org/docs/native-target-support.html.
        // Tier 1
        kotlin.macosX64("macos")
        kotlin.macosArm64()
        // Tier 2
        kotlin.linuxX64("linux")
        // Tier 3
        kotlin.mingwX64("windows")
    }

    private fun configureAllKotlinTargets(
        kotlin: KotlinMultiplatformExtension
    ): Unit = kotlin.targets.configureEach {
        this.compilations.configureEach {
            this.compilerOptions.configure {
                this.allWarningsAsErrors.set(true)
                this.languageVersion.set(KotlinVersion.KOTLIN_1_9)
            }
        }
    }

    private fun configureKotlinSourceSets(
        kotlin: KotlinMultiplatformExtension
    ): Unit = kotlin.sourceSets.run {
        val commonMain: KotlinSourceSet by this.getting
        val jvmAndNativeMain: KotlinSourceSet by this.creating
        jvmAndNativeMain.dependsOn(commonMain)
        val jvmMain: KotlinSourceSet by this.getting
        jvmMain.dependsOn(jvmAndNativeMain)
        val nativeMain: KotlinSourceSet by this.creating
        nativeMain.dependsOn(jvmAndNativeMain)
        val linuxMain: KotlinSourceSet by this.getting
        linuxMain.dependsOn(nativeMain)
        val macosMain: KotlinSourceSet by this.getting
        macosMain.dependsOn(nativeMain)
        val macosArm64Main: KotlinSourceSet by this.getting
        macosArm64Main.dependsOn(nativeMain)
        val windowsMain: KotlinSourceSet by this.getting
        windowsMain.dependsOn(nativeMain)
        this.configureEach {
            this.languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }

    fun publishing() {
        val publishing: PublishingExtension =
            this.project.extensions.findByType() ?: return
        val kotlinMultiplatform: NamedDomainObjectProvider<MavenPublication> by
        publishing.publications.existing(MavenPublication::class)
        kotlinMultiplatform.configure {
            this.groupId = this@KotlinMultiplatformExtensionManager.project
                .group
                .toString()
            this.artifactId =
                this@KotlinMultiplatformExtensionManager.project.name
            this.version = this@KotlinMultiplatformExtensionManager.project
                .version
                .toString()
        }
    }
}
