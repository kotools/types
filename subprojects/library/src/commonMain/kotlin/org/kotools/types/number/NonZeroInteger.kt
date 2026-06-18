package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [Integer] that is other than zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Key features</b>
 * </summary>
 *
 * ### Key features
 *
 * - **Creations:** Create from a [Long] or an [Integer] (see [fromLong] and
 * [fromInteger]).
 * - **Conversions:** Convert to its decimal string representation (see
 * [NonZeroInteger.toString]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class NonZeroInteger private constructor(
    private val value: Integer
) {
    /** Contains class-level declarations for the [NonZeroInteger] type. */
    public companion object {
        /**
         * Returns a [NonZeroInteger] representing the specified [value], or
         * throws an [IllegalArgumentException] if [value] is `0`.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromLong
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
         * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.fromLong
         * </details>
         * <br>
         *
         * See the [fromLongOrNull] function for returning `null` instead of
         * throwing an exception when [value] is `0`.
         */
        @JvmStatic
        public fun fromLong(value: Long): NonZeroInteger =
            fromInteger(Integer.of(value))

        /**
         * Returns a [NonZeroInteger] representing the specified [value], or
         * returns `null` if [value] is `0`.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromLong
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromLong] function for throwing an exception instead of
         * returning `null` when [value] is `0`.
         */
        @JvmSynthetic
        public fun fromLongOrNull(value: Long): NonZeroInteger? =
            fromIntegerOrNull(Integer.of(value))

        /**
         * Returns a [NonZeroInteger] representing the specified [value], or
         * throws an [IllegalArgumentException] if [value] represents zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromInteger
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
         * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.fromInteger
         * </details>
         * <br>
         *
         * See the [fromIntegerOrNull] function for returning `null` instead
         * of throwing an exception when [value] represents zero.
         */
        @JvmStatic
        public fun fromInteger(value: Integer): NonZeroInteger {
            if (value == Integer.of(0)) zeroIntegerError(value)
            return NonZeroInteger(value)
        }

        /**
         * Returns a [NonZeroInteger] representing the specified [value], or
         * returns `null` if [value] represents zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromInteger
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromInteger] function for throwing an exception instead
         * of returning `null` when [value] represents zero.
         */
        @JvmSynthetic
        public fun fromIntegerOrNull(value: Integer): NonZeroInteger? =
            if (value == Integer.of(0)) null else NonZeroInteger(value)

        private fun zeroIntegerError(value: Integer): Nothing {
            val message: String = errorMessage("Integer other than zero", value)
            throw IllegalArgumentException(message)
        }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the decimal string representation of this integer, delegating
     * to [Integer.toString].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.toStringOverride
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.value.toString()
}
