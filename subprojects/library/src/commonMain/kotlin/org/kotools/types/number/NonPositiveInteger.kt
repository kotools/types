package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmStatic

/**
 * Represents an [Integer] that is less than or equal to zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Key features</b>
 * </summary>
 *
 * ### Key features
 *
 * - **Creations:** Create from an [Integer] (see [fromInteger]).
 * - **Conversions:** Convert to its underlying [Integer] (see [toInteger]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class NonPositiveInteger private constructor(
    private val value: Integer
) {
    // --------------------------- Factory functions ---------------------------

    /** Contains class-level declarations for the [NonPositiveInteger] type. */
    public companion object {
        /**
         * Returns a [NonPositiveInteger] representing the specified [value],
         * or throws an [IllegalArgumentException] if [value] is positive.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.fromInteger
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
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.fromInteger
         * </details>
         */
        @JvmStatic
        public fun fromInteger(value: Integer): NonPositiveInteger {
            val zero: Integer = Integer.fromLong(0)
            if (value > zero) {
                val message: String = errorMessage("Positive integer", value)
                throw IllegalArgumentException(message)
            }
            return NonPositiveInteger(value)
        }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this integer as an [Integer].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.toInteger
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.toInteger
     * </details>
     */
    public fun toInteger(): Integer = this.value
}
