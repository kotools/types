package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.number.PlatformInteger
import kotlin.jvm.JvmStatic

/**
 * Represents a mathematical integer, with exact operations that never overflow.
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class Integer private constructor(
    private val delegate: PlatformInteger
) {
    // ------------------------------- Creations -------------------------------

    /** Contains type-level declarations for [Integer]. */
    public companion object {
        /**
         * Returns an [Integer] representing the specified [value].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.IntegerSample.of
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: org.kotools.types.number.IntegerJavaSample.of
         * </details>
         */
        @JvmStatic
        public fun of(value: Long): Integer {
            val delegate: PlatformInteger = PlatformInteger.of(value)
            return Integer(delegate)
        }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this integer.
     *
     * The resulting string is canonical, meaning that it has no leading plus
     * sign (`+`) and no leading zeros (`0`).
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.IntegerSample.toStringOverride
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.delegate.toString()
}
