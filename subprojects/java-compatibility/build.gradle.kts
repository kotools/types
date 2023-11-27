/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

plugins { java }

repositories.mavenCentral()

property("java.version")
    ?.toString()
    ?.let(JavaLanguageVersion::of)
    ?.let(java.toolchain.languageVersion::set)
    ?: error("The 'java.version' property wasn't found.")

dependencies {
    testImplementation(rootProject)
    testImplementation(project(":internal"))
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
