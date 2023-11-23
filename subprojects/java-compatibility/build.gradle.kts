/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

plugins { java }

repositories.mavenCentral()

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

dependencies {
    testImplementation(rootProject)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
