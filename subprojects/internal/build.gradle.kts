/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

import kotools.types.KotoolsTypesPackage

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("kotools.types.base")
    id("kotools.types.multiplatform")
}

repositories.mavenCentral()

kotlinCompile.javaPackagePrefix.set(KotoolsTypesPackage.Internal)

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonTestImplementation(libs.kotlin.test)
}
