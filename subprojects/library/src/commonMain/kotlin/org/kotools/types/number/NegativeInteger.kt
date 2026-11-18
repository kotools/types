package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [Integer] that is less than zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### Making strict negativity explicit
 *
 * "Less than zero" is a constraint that callers otherwise check inline on an
 * [Integer], and that is easy to forget or to get wrong at the boundary (zero
 * is not negative). This type moves that check to the construction of the
 * value, so that any function receiving a [NegativeInteger] can rely on it
 * without validating again.
 *
 * #### Sharing the sign guarantees of related types
 *
 * Every negative integer is also non-positive and non-zero. This type
 * converts to [NonPositiveInteger] and [NonZeroInteger] without any failure
 * case (see [toNonPositiveInteger] and [toNonZeroInteger]).
 * </details>
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
 * [structural equality][NegativeInteger.equals] (`x == y`, `x != y`).
 * - **Conversions:** Convert to a [NonPositiveInteger], a [NonZeroInteger], or
 * its underlying [Integer] (see [toNonPositiveInteger], [toNonZeroInteger] and
 * [toInteger]), or to its decimal string representation (see
 * [NegativeInteger.toString]).
 * </details>
 *
 * @since 5.3.0
 */
@ExperimentalKotoolsTypesApi
public class NegativeInteger private constructor(private val value: Integer) {
    // --------------------------- Factory functions ---------------------------

    /** Contains class-level declarations for the [NegativeInteger] type. */
    public companion object {
        /**
         * Returns a [NegativeInteger] representing the specified [value], or
         * throws an [IllegalArgumentException] if [value] is greater than or
         * equal to zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.fromLong
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
         * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.fromLong
         * </details>
         * <br>
         *
         * See the [fromLongOrNull] function for returning `null` instead of
         * throwing an exception when [value] is greater than or equal to zero.
         */
        @JvmStatic
        public fun fromLong(value: Long): NegativeInteger {
            val integer: Integer = Integer.fromLong(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NegativeInteger] representing the specified [value], or
         * returns `null` if [value] is greater than or equal to zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.fromLong
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromLong] function for throwing an exception instead of
         * returning `null` when [value] is greater than or equal to zero.
         */
        @JvmSynthetic
        public fun fromLongOrNull(value: Long): NegativeInteger? {
            val integer: Integer = Integer.fromLong(value)
            return this.fromIntegerOrNull(integer)
        }

        /**
         * Returns a [NegativeInteger] representing the specified [value], or
         * throws an [IllegalArgumentException] if [value] is greater than or
         * equal to zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.fromInteger
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
         * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.fromInteger
         * </details>
         * <br>
         *
         * See the [fromIntegerOrNull] function for returning `null` instead of
         * throwing an exception when [value] is greater than or equal to zero.
         */
        @JvmStatic
        public fun fromInteger(value: Integer): NegativeInteger {
            require(value < Integer.ZERO) {
                errorMessage("Non-negative integer", value)
            }
            return NegativeInteger(value)
        }

        /**
         * Returns a [NegativeInteger] representing the specified [value], or
         * returns `null` if [value] is greater than or equal to zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.fromInteger
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromInteger] function for throwing an exception instead of
         * returning `null` when [value] is greater than or equal to zero.
         */
        @JvmSynthetic
        public fun fromIntegerOrNull(value: Integer): NegativeInteger? = value
            .takeIf { it < Integer.ZERO }
            ?.let(::NegativeInteger)

        /**
         * Returns a [NegativeInteger] representing the number described by
         * [value], or throws [NumberFormatException] if [value] doesn't
         * represent an integer, or [IllegalArgumentException] if [value]
         * represents an integer that is greater than or equal to zero.
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
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.parsing
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
         * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.parsing
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid or non-negative [value].
         */
        @JvmStatic
        public fun parse(value: String): NegativeInteger {
            val integer: Integer = Integer.parse(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NegativeInteger] representing the number described by
         * [value], or returns `null` if [value] doesn't represent an integer
         * or represents an integer that is greater than or equal to zero.
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
         * SAMPLE: org.kotools.types.number.NegativeIntegerSample.parsing
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid or non-negative [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): NegativeInteger? = Integer
            .parseOrNull(value)
            ?.let(this::fromIntegerOrNull)
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [NegativeInteger] representing
     * the same numeric value as this one, or returns `false` otherwise.
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is NegativeInteger && this.value == other.value

    /**
     * Returns a hash code value for this integer.
     *
     * This function follows the contract of [Any.hashCode].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.NegativeInteger.toInt()
        return 31 * seed + this.value.hashCode()
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this integer as a [NonPositiveInteger].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.toNonPositiveInteger
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.toNonPositiveInteger
     * </details>
     */
    public fun toNonPositiveInteger(): NonPositiveInteger =
        NonPositiveInteger.fromInteger(this.value)

    /**
     * Returns this integer as a [NonZeroInteger].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.toNonZeroInteger
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.toNonZeroInteger
     * </details>
     */
    public fun toNonZeroInteger(): NonZeroInteger =
        NonZeroInteger.fromInteger(this.value)

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
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.toInteger
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.toInteger
     * </details>
     */
    public fun toInteger(): Integer = this.value

    /**
     * Returns the decimal string representation of this integer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NegativeIntegerSample.toStringOverride
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
     * SAMPLE: org.kotools.types.number.NegativeIntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.value.toString()
}
