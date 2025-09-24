package convention.documentation

import org.gradle.api.provider.Property

/**
 * The extension responsible for configuring the `convention.documentation.root`
 * Gradle plugin.
 */
public interface DocumentationRootExtension {
    /** The name of the module to document. */
    public val moduleName: Property<String>
}
