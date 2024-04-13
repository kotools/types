package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider

internal class DocumentationProperties(project: Project) {
    private val layout: ProjectLayout = project.layout

    val apiReferenceDirectory: Provider<Directory>
        get() = layout.buildDirectory.dir("api-reference")

    val copyrightNotice: String
        get() = layout.projectDirectory.file("LICENSE.txt")
            .asFile
            .useLines { lines: Sequence<String> ->
                lines.first { it.startsWith("Copyright (c)") }
            }

    val logoIcon: RegularFile
        get() = layout.projectDirectory.file("dokka/logo-icon.svg")
}
