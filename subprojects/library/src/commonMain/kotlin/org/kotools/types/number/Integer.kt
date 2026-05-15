package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.integerAddition
import org.kotools.types.internal.integerDivision
import org.kotools.types.internal.integerMultiplication
import org.kotools.types.internal.integerRemainder
import org.kotools.types.internal.integerSubtraction
import org.kotools.types.internal.number.PlatformInteger
import org.kotools.types.number.Integer.Companion.of
import org.kotools.types.number.Integer.Companion.parse
import org.kotools.types.number.Integer.Companion.parseOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

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
 * On JVM, this type coexists with `java.lang.Integer`. In Kotlin, developers
 * typically use [Int], so this rarely causes confusion in practice.
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
 * SAMPLE: org.kotools.types.number.IntegerSample.overflowProblem
 *
 * **Solution:** The [Integer] type can [add][plus], [subtract][minus] or
 * [multiply][times] integers without producing an overflow.
 *
 * SAMPLE: org.kotools.types.number.IntegerSample.overflowSolution
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
 * **Solution:** [Division][div] and [remainder][rem] operations by zero on
 * [Integer] type throw an [ArithmeticException] on all platforms.
 *
 * SAMPLE: org.kotools.types.number.IntegerSample.divisionByZeroSolution
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
 * - **Creations:** Create from [Long] number ([of]) or decimal string
 * ([parse]).
 * - **Comparisons:** Compare integers using
 * [structural equality][Integer.equals] (`x == y`, `x != y`) and
 * [ordering operators][compareTo] (`x < y`, `x <= y`, `x > y`, `x >= y`).
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
public class Integer private constructor(
    private val delegate: PlatformInteger
) {
    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        /**
         * Returns an [Integer] representing the specified [value].
         *
         * This function preserves the canonical representation of [value].
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
            val delegate = PlatformInteger(value)
            return Integer(delegate)
        }

        /**
         * Returns an [Integer] representing the number described by [value],
         * or throws [NumberFormatException] if the [value] doesn't represent an
         * integer.
         *
         * A valid integer representation is defined as:
         *
         * ```
         * integer = [sign] digit {digit}
         * sign = "+" | "-"
         * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * _(grammar written in Wirth-style EBNF, where `[]` denotes
         * optionality and `{}` denotes repetition)_
         *
         * Leading zeros and plus sign are ignored when interpreting [value]. As
         * a result, calling this function with `123` and `+000123` produces the
         * same integer.
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
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun parse(value: String): Integer {
            if (!value.isInteger()) throw NumberFormatException(
                "\"$value\" is not a valid integer."
            )
            val normalized: String = value.normalizeInteger()
            val delegate = PlatformInteger(normalized)
            return Integer(delegate)
        }

        /**
         * Returns an [Integer] representing the number described by [value],
         * or returns `null` if the [value] doesn't represent an integer.
         *
         * A valid integer representation is defined as:
         *
         * ```
         * integer = [sign] digit {digit}
         * sign = "+" | "-"
         * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * _(grammar written in Wirth-style EBNF, where `[]` denotes
         * optionality and `{}` denotes repetition)_
         *
         * Leading zeros and plus sign are ignored when interpreting [value]. As
         * a result, calling this function with `123` and `+000123` produces the
         * same integer.
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
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid [value].
         */
        @JvmSynthetic
        public fun parseOrNull(value: String): Integer? {
            if (!value.isInteger()) return null
            val normalized: String = value.normalizeInteger()
            val delegate = PlatformInteger(normalized)
            return Integer(delegate)
        }

        private fun String.isInteger(): Boolean {
            val range: CharRange = '0'..'9'
            return this.removePrefix("+")
                .removePrefix("-")
                .ifBlank { return false }
                .all { it in range }
        }

        private fun String.normalizeInteger(): String {
            val sign: String = this.firstOrNull { it == '-' }
                ?.toString()
                ?: ""
            val digits: String = this.removePrefix("+")
                .removePrefix("-")
                .trimStart('0')
                .ifEmpty { return "0" }
            return "$sign$digits"
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an [Integer] representing the
     * same numeric value as this one, or returns `false` otherwise.
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
     * This function follows the contract of [Any.hashCode], with an additional
     * property: if two instances of [Integer] are not equal, then calling this
     * function on these objects must produce different hash codes.
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
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.Integer.toInt()
        return 31 * seed + this.delegate.hashCode()
    }

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
    public operator fun compareTo(other: Integer): Int {
        val x: String = this.delegate.toString()
        val y: String = other.delegate.toString()
        return x.compareTo(y)
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
     * SAMPLE: org.kotools.types.number.IntegerSample.unaryMinus
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.unaryMinus
     * </details>
     */
    public operator fun unaryMinus(): Integer {
        val zero: Integer = of(0)
        if (this == zero) return this
        val minusSign = "-"
        val decimal: String = this.delegate.toString()
        val isNegative: Boolean = decimal.startsWith(minusSign)
        if (isNegative) {
            val text: String = decimal.removePrefix(minusSign)
            return parse(text)
        }
        return parse("$minusSign$decimal")
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.plus
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.plus
     * </details>
     */
    public operator fun plus(other: Integer): Integer {
        val sum: String = integerAddition(x = "$this", y = "$other")
        return parse(sum)
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.minus
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.minus
     * </details>
     */
    public operator fun minus(other: Integer): Integer {
        val difference: String = integerSubtraction(x = "$this", y = "$other")
        return parse(difference)
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
     * SAMPLE: org.kotools.types.number.IntegerSample.times
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.times
     * </details>
     */
    public operator fun times(other: Integer): Integer {
        val product: String = integerMultiplication(x = "$this", y = "$other")
        return parse(product)
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.div
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.div
     * </details>
     * <br>
     *
     * See the [divOrNull] function for returning `null` instead of throwing an
     * exception in case of invalid [other] integer.
     */
    public operator fun div(other: Integer): Integer {
        val zero: Integer = of(0)
        if (other == zero)
            throw ArithmeticException("Integer can't be divided by zero.")
        val quotient: String = integerDivision(x = "$this", y = "$other")
        return parse(quotient)
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.divOrNull
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
    public fun divOrNull(other: Integer): Integer? {
        val zero: Integer = of(0)
        if (other == zero) return null
        val quotient: String = integerDivision(x = "$this", y = "$other")
        return parse(quotient)
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.rem
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
     * SAMPLE: org.kotools.types.number.IntegerJavaSample.rem
     * </details>
     * <br>
     *
     * See the [remOrNull] function for returning `null` instead of throwing an
     * exception in case of invalid [other] integer.
     */
    public operator fun rem(other: Integer): Integer {
        val zero: Integer = of(0)
        if (other == zero)
            throw ArithmeticException("Integer can't be divided by zero.")
        val remainder: String = integerRemainder(x = "$this", y = "$other")
        return parse(remainder)
    }

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
     * SAMPLE: org.kotools.types.number.IntegerSample.remOrNull
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
    public fun remOrNull(other: Integer): Integer? {
        val zero: Integer = of(0)
        if (other == zero) return null
        val remainder: String = integerRemainder(x = "$this", y = "$other")
        return parse(remainder)
    }

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
