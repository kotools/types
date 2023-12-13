/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

import kotools.types.KotoolsTypesPackage

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

tasks.register("allJvmTests").configure {
    group = "verification"
    description = "Runs Kotlin/JVM tests on all projects."
    dependsOn += tasks.jvmTest
    subprojects.forEach { subproject: Project ->
        subproject.tasks.findByName("jvmTest")
            ?.let { dependsOn += it }
    }
    allprojects.find { it.name == "java-compatibility" }
        ?.tasks
        ?.findByName("test")
        ?.let { dependsOn += it }
}
tasks.register("unit")
