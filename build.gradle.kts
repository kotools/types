/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

import kotools.types.KotoolsTypesPackage
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
    id("kotools.types.publication")
}

group = "org.kotools"

repositories.mavenCentral()

kotlin.sourceSets {
    val nativeTest: KotlinSourceSet by creating {
        val commonTest: KotlinSourceSet = commonTest.get()
        dependsOn(commonTest)
    }
    linuxTest.get().dependsOn(nativeTest)
    macosTest.get().dependsOn(nativeTest)
    windowsTest.get().dependsOn(nativeTest)
}

kotlinCompile.javaPackagePrefix.set(KotoolsTypesPackage.Root)

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

tasks.register("unit")
tasks.register("devCheck").configure {
    description = "Run development checks, including JVM tests, documentation" +
            " generation and API checks."
    dependsOn(tasks.jvmTest, tasks.dokkaHtml, tasks.apiCheck)
}
