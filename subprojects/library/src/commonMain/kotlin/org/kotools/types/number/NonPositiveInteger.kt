package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import org.kotools.types.number.NonPositiveInteger.Companion.fromInteger
import org.kotools.types.number.NonPositiveInteger.Companion.fromIntegerOrNull
import org.kotools.types.number.NonPositiveInteger.Companion.fromLong
import org.kotools.types.number.NonPositiveInteger.Companion.fromLongOrNull
import org.kotools.types.number.NonPositiveInteger.Companion.parse
import org.kotools.types.number.NonPositiveInteger.Companion.parseOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [Integer] that is less than or equal to zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### Completing the sign trichotomy
 *
 * Once [NonZeroInteger] and [NonNegativeInteger] exist as standalone types,
 * "non-positive" is the natural remaining case: together, the three types
 * make the sign of an [Integer] fully and symmetrically expressible in the
 * type system, instead of leaving "less than or equal to zero" as a runtime
 * check that callers would otherwise perform inline on an [Integer].
 *
 * #### A closure-driven arithmetic surface
 *
 * [unaryMinus], [plus], [minus] with a [NonNegativeInteger] operand, and both
 * [times] overloads are defined because each always produces a value
 * representable as a [NonPositiveInteger] or a [NonNegativeInteger]:
 * negating a non-positive integer is always non-negative; adding two
 * non-positive integers stays non-positive; subtracting a non-negative
 * integer from a non-positive one stays non-positive (`x - y == x + (-y)`,
 * and `-y <= 0`); multiplying a non-positive integer by a non-negative one
 * stays non-positive; and multiplying two non-positive integers becomes
 * non-negative.
 *
 * [minus] with a [NonPositiveInteger], [NonZeroInteger], or [Integer]
 * operand is absent, because the set of non-positive integers isn't closed
 * under any of them: for two non-positive integers `x` and `y` where
 * `x > y`, `x - y` is positive (e.g. `-1 - (-5) = 4`), and the same
 * unrestricted sign applies when subtracting a [NonZeroInteger] or an
 * [Integer]. A [times] overload accepting a [NonZeroInteger] is absent for
 * the same reason: a non-zero integer can be positive or negative, so the
 * sign of the product is indeterminate. Use [toInteger] together with
 * [Integer.minus] or [Integer.times] for these cases.
 *
 * Division and remainder operations are absent, since they would only
 * duplicate the semantics already covered by [Integer.div] and [Integer.rem].
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
 * [structural equality][NonPositiveInteger.equals] (`x == y`, `x != y`).
 * - **Arithmetic operations:** [Negate][unaryMinus] (`-x`), [add][plus]
 * (`x + y`), [subtract][minus] (`x - y`), or [multiply][times] (`x * y`)
 * non-positive integers, each producing another non-positive or
 * non-negative integer depending on the operation. Some combinations are
 * intentionally absent, because the set of non-positive integers isn't
 * closed under them (e.g., `-1 - (-5) = 4`).
 * - **Conversions:** Convert to its underlying [Integer] (see [toInteger]),
 * or to its decimal string representation (see
 * [NonPositiveInteger.toString]).
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
            if (value > Integer.ZERO) {
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
        public fun fromIntegerOrNull(value: Integer): NonPositiveInteger? =
            if (value > Integer.ZERO) null
            else NonPositiveInteger(value)

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

        /**
         * Returns a [NonPositiveInteger] representing the number described
         * by [value], or returns `null` if [value] doesn't represent an
         * integer or represents a positive integer.
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
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid or positive [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): NonPositiveInteger? {
            val integer: Integer = Integer.parseOrNull(value) ?: return null
            return this.fromIntegerOrNull(integer)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [NonPositiveInteger]
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is NonPositiveInteger && this.value == other.value

    /**
     * Returns a hash code value for this integer.
     *
     * This function follows the contract of [Any.hashCode], with an
     * additional property: if two instances of [NonPositiveInteger] are not
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.NonPositiveInteger.toInt()
        return 31 * seed + this.value.hashCode()
    }

    // ------------------------- Arithmetic operations -------------------------

    /**
     * Returns the negative of this integer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.unaryMinus
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.unaryMinus
     * </details>
     */
    public operator fun unaryMinus(): NonNegativeInteger =
        NonNegativeInteger.fromInteger(-this.value)

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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.plus
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.plus
     * </details>
     */
    public operator fun plus(other: NonPositiveInteger): NonPositiveInteger =
        fromInteger(this.value + other.value)

    /**
     * Subtracts the [other] integer from this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.minus
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.minus
     * </details>
     */
    public operator fun minus(other: NonNegativeInteger): NonPositiveInteger =
        this + (-other)

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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.timesWithNonNegativeInteger
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.timesWithNonNegativeInteger
     * </details>
     */
    public operator fun times(other: NonNegativeInteger): NonPositiveInteger {
        val product: Integer = this.value * other.toInteger()
        return fromInteger(product)
    }

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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.timesWithNonPositiveInteger
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.timesWithNonPositiveInteger
     * </details>
     */
    public operator fun times(other: NonPositiveInteger): NonNegativeInteger =
        NonNegativeInteger.fromInteger(this.value * other.value)

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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerSample.toStringOverride
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
     * SAMPLE: org.kotools.types.number.NonPositiveIntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.value.toString()
}
