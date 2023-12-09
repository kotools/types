/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

import kotools.types.KotoolsTypesPackage
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("kotools.types.base")
    id("kotools.types.multiplatform")
}

repositories.mavenCentral()

kotlin.sourceSets {
    val nativeMain: KotlinSourceSet by creating {
        val commonMain: KotlinSourceSet = commonMain.get()
        dependsOn(commonMain)
    }
    linuxMain.get().dependsOn(nativeMain)
    macosMain.get().dependsOn(nativeMain)
    windowsMain.get().dependsOn(nativeMain)
}

kotlinCompile.javaPackagePrefix.set(KotoolsTypesPackage.Internal)

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonTestImplementation(libs.kotlin.test)
}
