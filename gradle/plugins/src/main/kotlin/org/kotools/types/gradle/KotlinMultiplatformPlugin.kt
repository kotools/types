package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
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

/** Gradle convention plugin for configuring Kotlin Multiplatform projects. */
public class KotlinMultiplatformPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.multiplatform")

        // ------------------------- Plugin extensions -------------------------

        val kotlin: KotlinMultiplatformExtension =
            project.extensions.getByType()
        kotlin.explicitApi()

        kotlin.js(KotlinJsCompilerType.IR) {
            this.nodejs { this.testTask(KotlinJsTest::useMocha) }
            this.binaries.library()
        }
        project.rootProject.plugins.withType<YarnPlugin>().configureEach {
            val yarn: YarnRootExtension =
                project.rootProject.extensions.getByType()
            yarn.lockFileDirectory =
                project.rootProject.layout.projectDirectory.asFile
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
            val jvmAndNativeMain: KotlinSourceSet by creating {
                dependsOn(commonMain)
            }
            val jvmMain: KotlinSourceSet by getting {
                dependsOn(jvmAndNativeMain)
            }
            val nativeMain: KotlinSourceSet by creating {
                dependsOn(jvmAndNativeMain)
            }
            val linuxMain: KotlinSourceSet by getting {
                dependsOn(nativeMain)
            }
            val macosMain: KotlinSourceSet by getting {
                dependsOn(nativeMain)
            }
            val macosArm64Main: KotlinSourceSet by getting {
                dependsOn(nativeMain)
            }
            val windowsMain: KotlinSourceSet by getting {
                dependsOn(nativeMain)
            }
            configureEach { languageSettings.optIn("kotlin.RequiresOptIn") }
        }

        project.extensions.findByType<PublishingExtension>()?.let {
            it.publications.named<MavenPublication>("kotlinMultiplatform")
                .configure {
                    groupId = "${project.group}"
                    artifactId = project.name
                    version = "${project.version}"
                }
        }

        // ------------------------------- Tasks -------------------------------

        val compileTestKotlinJs: TaskProvider<Kotlin2JsCompile>
                by project.tasks.existing(Kotlin2JsCompile::class)

        val compileTestDevelopmentExecutableKotlinJs:
                TaskProvider<KotlinJsIrLink>
                by project.tasks.existing(KotlinJsIrLink::class)
        compileTestDevelopmentExecutableKotlinJs.configure {
            dependsOn(compileTestKotlinJs)
        }

        val jsNodeTest: TaskProvider<Task> by project.tasks.existing

        val checkJs: TaskProvider<Task> by project.tasks.registering
        checkJs.configure {
            description = "Runs all checks for the Kotlin/JS platform."
            dependsOn(jsNodeTest)
        }

        val jvmTest: TaskProvider<Task> by project.tasks.existing

        val checkJvm: TaskProvider<Task> by project.tasks.registering
        checkJvm.configure {
            description = "Runs all checks for the Kotlin/JVM platform."
            dependsOn(jvmTest)
            val hasKotlinBinaryCompatibilityValidatorPlugin: Boolean =
                project.pluginManager.hasPlugin(
                    "org.jetbrains.kotlinx.binary-compatibility-validator"
                )
            if (hasKotlinBinaryCompatibilityValidatorPlugin) {
                val jvmApiCheck: TaskProvider<Task> by project.tasks.existing
                dependsOn(jvmApiCheck)
            }
        }

        project.tasks.withType<Jar>().configureEach {
            fun key(suffix: String): String = "Implementation-$suffix"
            val name: Pair<String, String> = key("Title") to project.name
            val version: Pair<String, Any> = key("Version") to project.version
            manifest.attributes(name, version)
        }
    }
}
