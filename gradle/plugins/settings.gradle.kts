/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

@Suppress("UnstableApiUsage")
dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}
