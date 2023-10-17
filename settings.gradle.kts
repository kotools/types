/*
 * Copyright 2022-2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)
