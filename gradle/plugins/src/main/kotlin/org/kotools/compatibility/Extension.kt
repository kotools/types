@file:JvmName("Extension")

package org.kotools.compatibility

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

@JvmSynthetic
internal fun CompatibilityExtension(project: Project): CompatibilityExtension =
    project.extensions.create("compatibility")

/** Extension configuring the [compatibility plugin][CompatibilityPlugin]. */
public interface CompatibilityExtension {
    /** Java version to be compatible with. */
    public val java: Property<String>

    /** Kotlin version to be compatible with. */
    public val kotlin: Property<String>
}
