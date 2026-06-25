package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import org.kotools.types.number.NonNegativeInteger.Companion.fromInteger
import org.kotools.types.number.NonNegativeInteger.Companion.fromIntegerOrNull
import org.kotools.types.number.NonNegativeInteger.Companion.fromLong
import org.kotools.types.number.NonNegativeInteger.Companion.fromLongOrNull
import org.kotools.types.number.NonNegativeInteger.Companion.parse
import org.kotools.types.number.NonNegativeInteger.Companion.parseOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [Integer] that is greater than or equal to zero.
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
 * - **Comparisons:** Compare integers using
 * [structural equality][NonNegativeInteger.equals] (`x == y`, `x != y`).
 * - **Arithmetic operations:** [Add][plus] (`x + y`) or [multiply][times]
 * (`x * y`) non-negative integers, always producing another non-negative
 * integer. Subtraction is intentionally absent, because the set of
 * non-negative integers isn't closed under this operation (e.g.,
 * `1 - 2 = -1`).
 * - **Conversions:** Convert to its underlying [Integer] (see [toInteger]),
 * or to its decimal string representation (see
 * [NonNegativeInteger.toString]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class NonNegativeInteger private constructor(
    private val value: Integer
) {
    // --------------------------- Factory functions ---------------------------

    /** Contains class-level declarations for the [NonNegativeInteger] type. */
    public companion object {
        /**
         * Returns a [NonNegativeInteger] representing the specified [value],
         * or throws an [IllegalArgumentException] if [value] is negative.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.fromLong
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
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.fromLong
         * </details>
         * <br>
         *
         * See the [fromLongOrNull] function for returning `null` instead of
         * throwing an exception when [value] is negative.
         */
        @JvmStatic
        public fun fromLong(value: Long): NonNegativeInteger {
            val integer: Integer = Integer.fromLong(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NonNegativeInteger] representing the specified [value],
         * or returns `null` if [value] is negative.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.fromLong
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromLong] function for throwing an exception instead of
         * returning `null` when [value] is negative.
         */
        @JvmSynthetic
        public fun fromLongOrNull(value: Long): NonNegativeInteger? {
            val integer: Integer = Integer.fromLong(value)
            return this.fromIntegerOrNull(integer)
        }

        /**
         * Returns a [NonNegativeInteger] representing the specified [value],
         * or throws an [IllegalArgumentException] if [value] is negative.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.fromInteger
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
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.fromInteger
         * </details>
         * <br>
         *
         * See the [fromIntegerOrNull] function for returning `null` instead
         * of throwing an exception when [value] is negative.
         */
        @JvmStatic
        public fun fromInteger(value: Integer): NonNegativeInteger {
            if (value < Integer.ZERO) {
                val message: String = errorMessage("Negative integer", value)
                throw IllegalArgumentException(message)
            }
            return NonNegativeInteger(value)
        }

        /**
         * Returns a [NonNegativeInteger] representing the specified [value],
         * or returns `null` if [value] is negative.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.fromInteger
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromInteger] function for throwing an exception instead
         * of returning `null` when [value] is negative.
         */
        @JvmSynthetic
        public fun fromIntegerOrNull(value: Integer): NonNegativeInteger? =
            if (value < Integer.ZERO) null
            else NonNegativeInteger(value)

        /**
         * Returns a [NonNegativeInteger] representing the number described
         * by [value], or throws [NumberFormatException] if [value] doesn't
         * represent an integer, or [IllegalArgumentException] if [value]
         * represents a negative integer.
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
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.parsing
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
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.parsing
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid or negative [value].
         */
        @JvmStatic
        public fun parse(value: String): NonNegativeInteger {
            val integer: Integer = Integer.parse(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NonNegativeInteger] representing the number described
         * by [value], or returns `null` if [value] doesn't represent an
         * integer or represents a negative integer.
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
         * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.parsing
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid or negative [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): NonNegativeInteger? {
            val integer: Integer = Integer.parseOrNull(value) ?: return null
            return this.fromIntegerOrNull(integer)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [NonNegativeInteger]
     * representing the same numeric value as this one, or returns `false`
     * otherwise.
     *
     * This function follows the contract of [Any.equals].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is NonNegativeInteger && this.value == other.value

    /**
     * Returns a hash code value for this integer.
     *
     * This function follows the contract of [Any.hashCode], with an
     * additional property: if two instances of [NonNegativeInteger] are not
     * equal, then calling this function on these objects must produce
     * different hash codes.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.NonNegativeInteger.toInt()
        return 31 * seed + this.value.hashCode()
    }

    // ------------------------- Arithmetic operations -------------------------

    /**
     * Adds the [other] integer to this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.plus
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.plus
     * </details>
     */
    public operator fun plus(other: NonNegativeInteger): NonNegativeInteger =
        fromInteger(this.value + other.value)

    /**
     * Multiplies this integer by the [other] one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.times
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.times
     * </details>
     */
    public operator fun times(other: NonNegativeInteger): NonNegativeInteger =
        fromInteger(this.value * other.value)

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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.toInteger
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.toInteger
     * </details>
     */
    public fun toInteger(): Integer = this.value

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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerSample.toStringOverride
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
     * SAMPLE: org.kotools.types.number.NonNegativeIntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.value.toString()
}
