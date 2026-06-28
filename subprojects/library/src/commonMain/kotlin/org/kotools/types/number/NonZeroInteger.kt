package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import org.kotools.types.number.NonZeroInteger.Companion.fromInteger
import org.kotools.types.number.NonZeroInteger.Companion.fromIntegerOrNull
import org.kotools.types.number.NonZeroInteger.Companion.fromLong
import org.kotools.types.number.NonZeroInteger.Companion.fromLongOrNull
import org.kotools.types.number.NonZeroInteger.Companion.parse
import org.kotools.types.number.NonZeroInteger.Companion.parseOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [Integer] that is other than zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### A standalone type instead of a runtime check
 *
 * Whether a divisor is zero could be checked at runtime, inline, every time a
 * division is about to happen. Instead, this library models "an [Integer]
 * that is other than zero" as its own type. This turns "the divisor isn't
 * zero" from a precondition that [Integer.div] and [Integer.rem] would
 * otherwise need to verify (and that callers would need to trust was
 * verified) into a guarantee carried by the [NonZeroInteger] type itself: any
 * value of this type is already known to be non-zero, so [Integer.div] and
 * [Integer.rem] can accept it as a divisor and never throw or return `null`
 * because of a zero divisor.
 *
 * #### A closure-driven arithmetic surface
 *
 * Only [unaryMinus] and [times] are defined as arithmetic operations on this
 * type, because the set of non-zero integers is closed under negation and
 * multiplication: negating or multiplying non-zero integers always produces
 * another non-zero integer.
 *
 * Addition and subtraction are intentionally absent, because the set of
 * non-zero integers isn't closed under either operation: for any non-zero
 * integer `x`, `x + (-x) = 0` and `x - x = 0`, so the result of these
 * operations on two non-zero integers can be zero, which isn't representable
 * as a [NonZeroInteger]. Use [toInteger] together with [Integer.plus] or
 * [Integer.minus] if you need to add or subtract non-zero integers.
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
 * [structural equality][NonZeroInteger.equals] (`x == y`, `x != y`).
 * - **Arithmetic operations:** [Negate][unaryMinus] (`-x`) or
 * [multiply][times] (`x * y`) non-zero integers, always producing another
 * non-zero integer. Addition and subtraction are intentionally absent,
 * because the set of non-zero integers isn't closed under these operations
 * (e.g., `x + (-x) = 0`).
 * - **Conversions:** Convert to its underlying [Integer] (see [toInteger]),
 * or to its decimal string representation (see [NonZeroInteger.toString]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class NonZeroInteger private constructor(private val value: Integer) {
    // --------------------------- Factory functions ---------------------------

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
        public fun fromLong(value: Long): NonZeroInteger {
            val integer: Integer = Integer.fromLong(value)
            return this.fromInteger(integer)
        }

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
        public fun fromLongOrNull(value: Long): NonZeroInteger? {
            val integer: Integer = Integer.fromLong(value)
            return this.fromIntegerOrNull(integer)
        }

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
            if (value == Integer.ZERO) {
                val message: String = errorMessage("Zero integer")
                throw IllegalArgumentException(message)
            }
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
            if (value == Integer.ZERO) null
            else NonZeroInteger(value)

        /**
         * Returns a [NonZeroInteger] representing the number described by
         * [value], or throws [NumberFormatException] if [value] doesn't
         * represent an integer, or [IllegalArgumentException] if [value]
         * represents zero.
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
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.parsing
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
         * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.parsing
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid or zero [value].
         */
        @JvmStatic
        public fun parse(value: String): NonZeroInteger {
            val integer: Integer = Integer.parse(value)
            return this.fromInteger(integer)
        }

        /**
         * Returns a [NonZeroInteger] representing the number described by
         * [value], or returns `null` if [value] doesn't represent an integer
         * or represents zero.
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
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.parsing
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid or zero [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): NonZeroInteger? {
            val integer: Integer = Integer.parseOrNull(value) ?: return null
            return this.fromIntegerOrNull(integer)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [NonZeroInteger] representing
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is NonZeroInteger && this.value == other.value

    /**
     * Returns a hash code value for this integer.
     *
     * This function follows the contract of [Any.hashCode], with an
     * additional property: if two instances of [NonZeroInteger] are not
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.NonZeroInteger.toInt()
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.unaryMinus
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.unaryMinus
     * </details>
     */
    public operator fun unaryMinus(): NonZeroInteger = fromInteger(-this.value)

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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.times
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.times
     * </details>
     */
    public operator fun times(other: NonZeroInteger): NonZeroInteger =
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.toInteger
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
     * SAMPLE: org.kotools.types.number.NonZeroIntegerJavaSample.toInteger
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
