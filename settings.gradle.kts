/*
 * Copyright 2022-2023 Loïc Lamarque, Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

include(KotoolsTypesSubproject.JavaCompatibility)

fun include(subproject: KotoolsTypesSubproject) {
    include("$subproject")
    project(":$subproject").projectDir =
        rootDir.resolve("subprojects/$subproject")
}

enum class KotoolsTypesSubproject(private val value: String) {
    JavaCompatibility("java-compatibility");

    override fun toString(): String = value
}
