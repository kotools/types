/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

include(KotoolsTypesSubproject.Core)
include(KotoolsTypesSubproject.Internal)
include(KotoolsTypesSubproject.JavaCompatibility)

fun include(subproject: KotoolsTypesSubproject) {
    include("$subproject")
    project(":$subproject").projectDir =
        rootDir.resolve("subprojects/$subproject")
}

enum class KotoolsTypesSubproject(private val value: String) {
    Core("core"),
    Internal("internal"),
    JavaCompatibility("java-compatibility");

    init {
        require(value.isNotBlank()) { "Subproject name shouldn't be blank." }
    }

    override fun toString(): String = value
}
