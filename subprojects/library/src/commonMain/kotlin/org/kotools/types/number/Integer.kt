package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.number.PlatformInteger
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents a mathematical integer, with exact arithmetic.
 *
 * Unlike Kotlin's built-in integer types ([Byte], [Short], [Int] and [Long]),
 * the [Integer] type provides exact arithmetic without overflow, and has
 * consistent behavior across all platforms when dividing an integer by zero.
 *
 * Designed to replace primitive integer types where correctness matters, this
 * type intentionally avoids name like `BigInteger` or `BigInt`, as its behavior
 * is part of its contract and not an implementation detail.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### Integer overflow
 *
 * **Problem:** Adding, subtracting or multiplying Kotlin integer types can lead
 * to an overflow, which produces unexpected behavior.
 *
 * SAMPLE: org.kotools.types.number.IntegerSample.overflowProblem
 *
 * **Solution:** The [Integer] type can add, subtract or multiply integers
 * without producing an overflow.
 *
 * TODO: Add Kotlin sample
 *
 * #### Division by zero
 *
 * **Problem:** Performing division and remainder operations by zero on Kotlin
 * integer types have different behavior per platform: throw an
 * [ArithmeticException] on JVM and Native platforms, and return `0` on JS
 * platform.
 *
 * SAMPLE: org.kotools.types.number.IntegerJvmNativeSample.divisionByZeroProblem
 *
 * SAMPLE: org.kotools.types.number.IntegerJsSample.divisionByZeroProblem
 *
 * **Solution:** Division and remainder operations by zero on [Integer] type
 * throw an [ArithmeticException] on all platforms.
 *
 * TODO: Add Kotlin sample
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class Integer private constructor(
    private val delegate: PlatformInteger
) {
    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        /**
         * Returns an [Integer] representing exactly the specified [value].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.IntegerSample.ofLong
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
         * SAMPLE: org.kotools.types.number.IntegerJavaSample.ofLong
         * </details>
         */
        @JvmStatic
        public fun of(value: Long): Integer {
            val delegate = PlatformInteger(value)
            return Integer(delegate)
        }

        /**
         * Returns an [Integer] representing exactly the number described by
         * [value], or throws [NumberFormatException] if the [value] doesn't
         * represent an integer.
         *
         * The specified [value] must be a numeric string, with an optional
         * leading sign (`+` or `-`). Also, this function removes insignificant
         * leading zeros from the [value]. As a result, calling this function
         * with `123` and `+000123` produces the same result.
         *
         * ```
         * integer = [sign] digit {digit}
         * sign = "+" | "-"
         * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.IntegerSample.parse
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
         * SAMPLE: org.kotools.types.number.IntegerJavaSample.parse
         * </details>
         * <br>
         *
         * See the [Integer.Companion.parseOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun parse(value: String): Integer {
            val delegate: PlatformInteger = PlatformInteger.parse(value)
            return Integer(delegate)
        }

        /**
         * Returns an [Integer] representing exactly the number described by
         * [value], or returns `null` if the [value] doesn't represent an
         * integer.
         *
         * The specified [value] must be a numeric string, with an optional
         * leading sign (`+` or `-`). Also, this function removes insignificant
         * leading zeros from the [value]. As a result, calling this function
         * with `123` and `+000123` produces the same result.
         *
         * ```
         * integer = [sign] digit {digit}
         * sign = "+" | "-"
         * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.IntegerSample.parseOrNull
         * </details>
         * <br>
         *
         * This function is not available from Java code, due to its
         * non-explicit support for nullable types.
         *
         * See the [Integer.Companion.parse] function for throwing an exception
         * instead of returning `null` in case of invalid [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): Integer? = PlatformInteger
            .parseOrNull(value)
            ?.let(::Integer)
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an [Integer] representing the
     * same numeric value as this one, or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.IntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is Integer && this.delegate == other.delegate

    /**
     * Returns a hash code value for this integer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.IntegerSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int = this.delegate.hashCode()

    /**
     * Compares this integer with the [other] one for order.
     *
     * Returns a negative number, zero, or a positive number as this integer is
     * less than, equal to, or greater than the [other] one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.IntegerSample.compareTo
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.compareTo
     * </details>
     */
    public operator fun compareTo(other: Integer): Int =
        this.delegate.compareTo(other.delegate)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this integer.
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
