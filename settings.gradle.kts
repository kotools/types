/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

subprojects("internal", "java-compatibility")

fun subprojects(first: String, vararg others: String) {
    val values: Set<String> = setOf(first) + others
    values.forEach {
        require(it.isNotBlank()) { "Subproject name shouldn't be blank." }
        include(it)
        project(":$it").projectDir = rootDir.resolve("subprojects/$it")
    }
}
