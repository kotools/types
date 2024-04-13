package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import java.io.File

internal fun DocumentationExtension(project: Project): DocumentationExtension {
    val documentation: DocumentationExtension =
        project.extensions.create("documentation")
    project.layout.projectDirectory.file("LICENSE.txt")
        .asFile
        .useLines { lines: Sequence<String> ->
            lines.first { it.startsWith("Copyright (c)") }
        }
        .let(documentation.copyrightNotice::convention)
    project.layout.projectDirectory.file("dokka/logo-icon.svg")
        .asFile
        .let(documentation.logoIcon::convention)
    project.layout.buildDirectory.dir("api-reference")
        .let(documentation.outputDirectory::convention)
    return documentation
}

/** The extension responsible for configuring the [DocumentationPlugin]. */
public interface DocumentationExtension {
    /**
     * The copyright notice to display in the documentation's footer.
     * By default, this property is set to the first copyright notice from the
     * license file of the project.
     */
    public val copyrightNotice: Property<String>

    /**
     * The logo to display in the documentation's header.
     * By default, this property is set to the `dokka/logo-icon.svg` file from
     * the project directory.
     */
    public val logoIcon: Property<File>

    /** The name of the module to document. */
    public val moduleName: Property<String>

    /**
     * The build directory that will contain the output of all tasks.
     * By default, this property is set to the `api-reference` directory in the
     * build directory.
     */
    public val outputDirectory: DirectoryProperty
}
