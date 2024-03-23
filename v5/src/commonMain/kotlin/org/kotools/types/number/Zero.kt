package org.kotools.types.number

import org.kotools.types.ExperimentalApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/** Represents the [zero](https://en.wikipedia.org/wiki/0) number. */
@ExperimentalApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public object Zero {
    /**
     * Returns the string representation of this number.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val message: String = Zero.toString()
     * println(message) // 0
     * ```
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final String message = Zero.INSTANCE.toString();
     * System.out.println(message); // 0
     * ```
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = "0"
}
