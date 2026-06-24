package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

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
 * - **Creations:** Create from a [Long], an [Integer], or a decimal string
 * (see [fromLong], [fromInteger] and [parse]).
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
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.fromLong
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
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.fromLong
         * </details>
         * <br>
         *
         * See the [fromLongOrNull] function for returning `null` instead of
         * throwing an exception when [value] is positive.
         */
        @JvmStatic
        public fun fromLong(value: Long): NonPositiveInteger {
            val integer: Integer = Integer.fromLong(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NonPositiveInteger] representing the specified [value],
         * or returns `null` if [value] is positive.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.fromLong
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromLong] function for throwing an exception instead of
         * returning `null` when [value] is positive.
         */
        @JvmSynthetic
        public fun fromLongOrNull(value: Long): NonPositiveInteger? {
            val integer: Integer = Integer.fromLong(value)
            return this.fromIntegerOrNull(integer)
        }

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
         * <br>
         *
         * See the [fromIntegerOrNull] function for returning `null` instead
         * of throwing an exception when [value] is positive.
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

        /**
         * Returns a [NonPositiveInteger] representing the specified [value],
         * or returns `null` if [value] is positive.
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
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromInteger] function for throwing an exception instead
         * of returning `null` when [value] is positive.
         */
        @JvmSynthetic
        public fun fromIntegerOrNull(value: Integer): NonPositiveInteger? {
            val zero: Integer = Integer.fromLong(0)
            return if (value > zero) null else NonPositiveInteger(value)
        }

        /**
         * Returns a [NonPositiveInteger] representing the number described
         * by [value], or throws [NumberFormatException] if [value] doesn't
         * represent an integer, or [IllegalArgumentException] if [value]
         * represents a positive integer.
         *
         * See the [Integer.Companion.parse] function for the grammar of a
         * valid integer representation.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.parsing
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
         * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.parsing
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid or positive [value].
         */
        @JvmStatic
        public fun parse(value: String): NonPositiveInteger {
            val integer: Integer = Integer.parse(value)
            return this.fromInteger(integer)
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
