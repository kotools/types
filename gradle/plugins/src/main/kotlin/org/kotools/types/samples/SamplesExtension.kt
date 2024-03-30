package org.kotools.types.samples

import org.gradle.api.file.DirectoryProperty

/** The extension customizing the `org.kotools.types.samples` plugin. */
public interface SamplesExtension {
    /** The source directory containing the samples to inline. */
    public val source: DirectoryProperty
}
