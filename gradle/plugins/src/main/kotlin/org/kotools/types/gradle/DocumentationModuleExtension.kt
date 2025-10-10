package org.kotools.types.gradle

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import java.io.File

/**
 * The extension responsible for configuring the
 * `convention.documentation.module` Gradle plugin.
 */
public interface DocumentationModuleExtension {
    /**
     * Flag for excluding this subproject from the API reference of the parent
     * project. Set to `false` by default.
     */
    public val excludeFromParentApiReference: Property<Boolean>

    /**
     * Links to use for resolving this project's dependencies in the
     * documentation.
     */
    public val externalLinks: ListProperty<String>

    /** The file containing module and package documentation. */
    public val packages: Property<File>
}
