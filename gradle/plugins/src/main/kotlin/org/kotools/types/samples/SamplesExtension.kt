package org.kotools.types.samples

import org.gradle.api.Project
import org.gradle.api.provider.Property

/** The extension customizing the `org.kotools.types.samples` plugin. */
public abstract class SamplesExtension {
    /** The project containing the samples to inline. */
    public abstract val project: Property<Project>

    internal var failOnSamplesInKDoc: Boolean = false
        private set

    /** Indicates that the build should fail if sources contain KDoc samples. */
    public fun sourcesWithoutInlinedSamples() {
        this.failOnSamplesInKDoc = true
    }
}
