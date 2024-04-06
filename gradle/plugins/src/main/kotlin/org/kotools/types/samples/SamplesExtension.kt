package org.kotools.types.samples

import org.gradle.api.Project
import org.gradle.api.provider.Property

/** The extension customizing the `org.kotools.types.samples` plugin. */
public interface SamplesExtension {
    /** The project containing the samples to inline. */
    public val project: Property<Project>
}
