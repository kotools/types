package org.kotools.types

import org.kotools.types.Integer.Companion.from
import org.kotools.types.Integer.Companion.fromDecimal
import org.kotools.types.Integer.Companion.fromDecimalOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

@OptIn(ExperimentalKotoolsTypesApi::class)
internal expect fun Integer(text: String): Integer

/**
 * Represents an integer.
 *
 * Use this type for preventing overflow when performing arithmetic operations
 * with integers, and for consistent behavior across all platforms when dividing
 * an integer by zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Integer vs Int</b>
 * </summary>
 *
 * ### Integer vs Int
 *
 * The [Integer] type is the default integer type provided by Kotools Types,
 * designed to replace primitive integer types where correctness matters.
 *
 * It models a mathematical integer and provides exact arithmetic without
 * overflow, unlike Kotlin's built-in number types ([Byte], [Short], [Int] and
 * [Long]).
 *
 * This type intentionally avoids names like `BigInteger` or `BigInt`, as its
 * behavior is part of its contract and not an implementation detail.
 *
 * On JVM, this type coexists with [`java.lang.Integer`](https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html).
 * In Kotlin, developers typically use [Int], so this rarely causes confusion in
 * practice.
 * </details>
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
 * **Problem:** Adding, subtracting or multiplying Kotlin integer types ([Byte],
 * [Short], [Int] and [Long]) can lead to an overflow, which produces unexpected
 * behavior.
 *
 * SAMPLE: org.kotools.types.IntegerSample.overflowProblem
 *
 * **Solution:** The [Integer] type can [add][plus], [subtract][minus] or
 * [multiply][times] integers without producing an overflow.
 *
 * SAMPLE: org.kotools.types.IntegerSample.overflowSolution
 *
 * #### Division by zero
 *
 * **Problem:** Performing division and remainder operations by zero on Kotlin
 * integer types have different behavior per platform: throw an
 * [ArithmeticException] on JVM and Native platforms, and return `0` on JS
 * platform.
 *
 * SAMPLE: org.kotools.types.IntegerJvmNativeSample.divisionByZeroProblem
 *
 * SAMPLE: org.kotools.types.IntegerJsSample.divisionByZeroProblem
 *
 * **Solution:** [Division][div] and [remainder][rem] operations by zero on
 * [Integer] type throw an [ArithmeticException] on all platforms.
 *
 * SAMPLE: org.kotools.types.IntegerSample.divisionByZeroSolution
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
 * - **Creations:** Create from [Long] number ([from]) or decimal string
 * ([fromDecimal]).
 * - **Comparisons:** Compare integers using
 * [structural equality][Integer.equals] (`x == y`, `x != y`) and
 * [ordering][compareTo] (`x < y`, `x <= y`, `x > y`, `x >= y`) operators.
 * - **Arithmetic operations:** [Add][plus] (`x + y`), [subtract][minus]
 * (`x - y`), [multiply][times] (`x * y`), [divide][div] (`x / y`), compute
 * [remainders][rem] (`x % y`), and [negate][unaryMinus] (`-x`) integers without
 * overflow.
 * - **Conversions:** Convert to its [decimal string][Integer.toString]
 * representation.
 * </details>
 *
 * @since 5.1.0
 */
@ExperimentalKotoolsTypesApi
public interface Integer {
    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        /**
         * Creates an [Integer] from the specified [number].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.IntegerSample.from
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
         * SAMPLE: org.kotools.types.IntegerJavaSample.from
         * </details>
         */
        @JvmStatic
        public fun from(number: Long): Integer = Integer("$number")

        /**
         * Creates an [Integer] from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't represent an
         * integer.
         *
         * The [text] parameter must only contain an optional plus sign (`+`) or
         * minus sign (`-`), followed by a sequence of digits (e.g., `1234`,
         * `+1234`, `-1234`).
         *
         * Also, the [text] is normalized by removing insignificant leading
         * zeros. As a result, calling this function with `1` and `01` produces
         * the same result.
         *
         * In case of invalid [text], this function throws an
         * [IllegalArgumentException] instead of a [NumberFormatException] to
         * ensure consistent behavior across all Kotlin platforms and to better
         * reflect invalid argument semantics.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.IntegerSample.fromDecimal
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
         * SAMPLE: org.kotools.types.IntegerJavaSample.fromDecimal
         * </details>
         * <br>
         *
         * See the [fromDecimalOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        public fun fromDecimal(text: String): Integer {
            require(this.isValid(text)) { "\"$text\" is not a valid integer." }
            val normalizedText: String = Decimal.normalize(text)
            return Integer(normalizedText)
        }

        /**
         * Creates an [Integer] from the specified [text], or returns `null` if
         * the [text] doesn't represent an integer.
         *
         * The [text] parameter must only contain an optional plus sign (`+`) or
         * minus sign (`-`), followed by a sequence of digits (e.g., `1234`,
         * `+1234`, `-1234`).
         *
         * Also, the [text] is normalized by removing insignificant leading
         * zeros. As a result, calling this function with `1` and `01` produces
         * the same result.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.IntegerSample.fromDecimalOrNull
         * </details>
         * <br>
         *
         * This function is not available from Java code, due to its
         * non-explicit [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [fromDecimal] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @JvmSynthetic
        public fun fromDecimalOrNull(text: String): Integer? {
            if (!this.isValid(text)) return null
            val normalizedText: String = Decimal.normalize(text)
            return Integer(normalizedText)
        }

        @JvmSynthetic
        internal fun isValid(text: String): Boolean =
            text matches Regex("""^[+-]?\d+$""")

        @JvmSynthetic
        internal fun zero(): Integer = this.from(0)

        @JvmSynthetic
        internal fun one(): Integer = this.from(1)
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an instance of [Integer] with
     * the same numeric value as this integer, or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.equalsOverride
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.equalsOverride
     * </details>
     */
    override fun equals(other: Any?): Boolean

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
     * SAMPLE: org.kotools.types.IntegerSample.hashCodeOverride
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.hashCodeOverride
     * </details>
     */
    override fun hashCode(): Int

    /**
     * Compares this integer with the [other] one for order.
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
     * SAMPLE: org.kotools.types.IntegerSample.compareTo
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.compareTo
     * </details>
     */
    public operator fun compareTo(other: Integer): Int

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
     * SAMPLE: org.kotools.types.IntegerSample.unaryMinus
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.unaryMinus
     * </details>
     */
    public operator fun unaryMinus(): Integer

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
     * SAMPLE: org.kotools.types.IntegerSample.plus
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.plus
     * </details>
     */
    public operator fun plus(other: Integer): Integer

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
     * SAMPLE: org.kotools.types.IntegerSample.minus
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.minus
     * </details>
     */
    public operator fun minus(other: Integer): Integer

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
     * SAMPLE: org.kotools.types.IntegerSample.times
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.times
     * </details>
     */
    public operator fun times(other: Integer): Integer

    /**
     * Returns the quotient of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.div
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.div
     * </details>
     * <br>
     *
     * See the [divOrNull] function for returning `null` instead of throwing an
     * exception in case of invalid [other] integer.
     */
    public operator fun div(other: Integer): Integer = this.divOrNull(other)
        ?: throw ArithmeticException("Integer can't be divided by zero.")

    /**
     * Returns the quotient of dividing this integer by the [other] one, or
     * returns `null` if the [other] integer is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.divOrNull
     * </details>
     * <br>
     *
     * This function is not available from Java code, due to its
     * non-explicit [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
     *
     * See the [div] function for throwing an exception instead of returning
     * `null` in case of invalid [other] integer.
     */
    @JvmSynthetic
    public fun divOrNull(other: Integer): Integer?

    /**
     * Returns the remainder of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.rem
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.rem
     * </details>
     * <br>
     *
     * See the [remOrNull] function for returning `null` instead of throwing an
     * exception in case of invalid [other] integer.
     */
    public operator fun rem(other: Integer): Integer = this.remOrNull(other)
        ?: throw ArithmeticException("Integer can't be divided by zero.")

    /**
     * Returns the remainder of dividing this integer by the [other] one, or
     * returns `null` if the [other] integer is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.remOrNull
     * </details>
     * <br>
     *
     * This function is not available from Java code, due to its
     * non-explicit [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
     *
     * See the [rem] function for throwing an exception instead of returning
     * `null` in case of invalid [other] integer.
     */
    @JvmSynthetic
    public fun remOrNull(other: Integer): Integer?

    // ------------------------------ Conversions ------------------------------

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
     * SAMPLE: org.kotools.types.IntegerSample.toStringOverride
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.toStringOverride
     * </details>
     * <br>
     *
     * See the [toSignedString] function for returning the signed decimal
     * representation of this integer.
     */
    override fun toString(): String

    /**
     * Returns the decimal string representation of this integer, prefixed with
     * its sign.
     *
     * When this integer is:
     * - zero, the returned decimal doesn't have a sign.
     * - positive (`> 0`), the returned decimal is signed with a plus (`+`).
     * - negative (`< 0`), the returned decimal is signed with a minus (`-`).
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.IntegerSample.toSignedString
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.toSignedString
     * </details>
     * <br>
     *
     * See the [Integer.toString] function for returning the unsigned decimal
     * representation of this integer.
     *
     * @since 5.2.0
     */
    public fun toSignedString(): String {
        val zero: Integer = zero()
        return if (this == zero || this < zero) this.toString()
        else "+$this"
    }
}
