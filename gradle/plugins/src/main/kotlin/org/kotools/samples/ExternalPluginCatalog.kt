package org.kotools.samples

/**
 * Represents a catalog of external plugins that Kotools Samples may depend on.
 *
 * @constructor Creates a catalog of external plugins.
 */
internal class ExternalPluginCatalog internal constructor() {
    /** The Dokka plugin. */
    internal val dokka: ExternalPlugin =
        ExternalPlugin(name = "Dokka", identifier = "org.jetbrains.dokka")

    /** The Kotlin Multiplatform plugin. */
    internal val kotlinMultiplatform: ExternalPlugin = ExternalPlugin(
        name = "Kotlin Multiplatform",
        identifier = "org.jetbrains.kotlin.multiplatform"
    )
}
