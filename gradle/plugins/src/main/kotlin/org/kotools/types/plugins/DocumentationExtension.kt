package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import java.io.File

internal fun DocumentationExtension(project: Project): DocumentationExtension {
    val extension: DocumentationExtension =
        project.extensions.create("documentation")
    project.layout.projectDirectory.dir("documentation/api-reference")
        .let(extension.archiveParentDirectory::convention)
    project.rootProject.layout.projectDirectory.file("LICENSE.txt")
        .asFile
        .useLines { lines: Sequence<String> ->
            lines.first { it.startsWith("Copyright (c)") }
        }
        .let(extension.copyrightNotice::convention)
    extension.excludeFromParentApiReference.convention(false)
    project.rootProject.layout.projectDirectory
        .file("documentation/api-reference/logo-icon.svg")
        .asFile
        .let(extension.logoIcon::convention)
    project.layout.buildDirectory.dir("api-reference")
        .let(extension.outputDirectory::convention)
    return extension
}

/** The extension responsible for configuring the [DocumentationPlugin]. */
public interface DocumentationExtension {
    /**
     * The parent directory of the API reference's archives.
     * Set to the `documentation/api-reference` directory from the project
     * directory by default.
     */
    public val archiveParentDirectory: DirectoryProperty

    /**
     * The copyright notice to display in the documentation's footer.
     * By default, this property is set to the first copyright notice from the
     * license file of the root project.
     */
    public val copyrightNotice: Property<String>

    /**
     * Flag for excluding this subproject from the API reference of the parent
     * project.
     * Set to `false` by default.
     */
    public val excludeFromParentApiReference: Property<Boolean>

    /**
     * The logo to display in the documentation's header.
     * By default, this property is set to the
     * `documentation/api-reference/logo-icon.svg` file from the root project
     * directory.
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

    /** The file containing the documentation of packages. */
    public val packages: Property<File>
}
