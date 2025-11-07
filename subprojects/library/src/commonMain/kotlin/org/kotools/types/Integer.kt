package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import org.kotools.types.internal.integerAddition
import org.kotools.types.internal.integerDivision
import org.kotools.types.internal.integerMultiplication
import org.kotools.types.internal.integerSubtraction
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an integer.
 *
 * Use this type for preventing overflow when performing arithmetic operations
 * with integers.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * **Problem:** Adding, subtracting or multiplying Kotlin integer types ([Byte],
 * [Short], [Int] and [Long]) can lead to an overflow, which produces unexpected
 * behavior.
 *
 * SAMPLE: [org.kotools.types.IntegerSample.overflowProblem]
 *
 * **Solution:** This type can [add][Integer.plus], [subtract][Integer.minus] or
 * [multiply][Integer.times] integers without producing an overflow.
 *
 * SAMPLE: [org.kotools.types.IntegerSample.overflowSolution]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Key features</b>
 * </summary>
 *
 * **Capabilities:**
 *
 * - **Creations:** Create from [Long] number ([from][Integer.Companion.from])
 * or decimal string ([fromDecimal][Integer.Companion.fromDecimal]).
 * - **Comparisons:** Compare integers using
 * [structural equality][Integer.equals] (`x == y`, `x != y`) and
 * [ordering][Integer.compareTo] (`x < y`, `x <= y`, `x > y`, `x >= y`)
 * operators.
 * - **Arithmetic operations:** [Negate][Integer.unaryMinus] (`-x`),
 * [add][Integer.plus] (`x + y`), [subtract][Integer.minus] (`x - y`),
 * [multiply][Integer.times] (`x * y`) and [divide][Integer.div] (`x / y`)
 * integers without overflow.
 * - **Conversions:** Convert to its [decimal string][Integer.toString]
 * representation.
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class Integer private constructor(private val decimal: String) {
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
         * SAMPLE: [org.kotools.types.IntegerSample.from]
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
         * SAMPLE: [org.kotools.types.IntegerJavaSample.from]
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
         * SAMPLE: [org.kotools.types.IntegerSample.fromDecimal]
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
         * SAMPLE: [org.kotools.types.IntegerJavaSample.fromDecimal]
         * </details>
         * <br>
         *
         * See the [fromDecimalOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        public fun fromDecimal(text: String): Integer {
            require(text.isNotBlank()) { "Integer can't be blank." }
            val textWithoutPlusSignPrefix: String = text.removePrefix("+")
            val unsignedText: String =
                textWithoutPlusSignPrefix.removePrefix("-")
            val isDecimal: Boolean =
                unsignedText.isNotEmpty() && unsignedText.all(Char::isDigit)
            require(isDecimal) {
                "Integer can only contain an optional + or - sign, followed " +
                        "by a sequence of digits (was: $text)."
            }
            val isZero: Boolean = unsignedText.all { it == '0' }
            if (isZero) return this.zero()
            val sign: String =
                if (textWithoutPlusSignPrefix.startsWith('-')) "-"
                else ""
            val digits: String = unsignedText.trimStart('0')
            return Integer(decimal = "$sign$digits")
        }

        /**
         * Creates an [Integer] from the specified [text], or returns `null` if
         * the [text] doesn't represent an integer.
         *
         * The [text] parameter must only contain an optional plus sign (`+`) or
         * minus sign (`-`), followed by a sequence of digits (e.g., `1234`,
         * `+1234`, `-1234`).
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.IntegerSample.fromDecimalOrNull]
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
            if (text.isBlank()) return null
            val textWithoutPlusSignPrefix: String = text.removePrefix("+")
            val unsignedText: String =
                textWithoutPlusSignPrefix.removePrefix("-")
            val isDecimal: Boolean =
                unsignedText.isNotEmpty() && unsignedText.all(Char::isDigit)
            if (!isDecimal) return null
            val isZero: Boolean = unsignedText.all { it == '0' }
            if (isZero) return this.zero()
            val sign: String =
                if (textWithoutPlusSignPrefix.startsWith('-')) "-"
                else ""
            val digits: String = unsignedText.trimStart('0')
            return Integer(decimal = "$sign$digits")
        }

        @JvmSynthetic
        internal fun zero(): Integer = from(0)
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an instance of [Integer] with the
     * same value as this one, or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.IntegerSample.equalsOverride]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is Integer && this.decimal == other.decimal

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
     * SAMPLE: [org.kotools.types.IntegerSample.hashCodeOverride]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = this.decimal.hashCode()

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
     * SAMPLE: [org.kotools.types.IntegerSample.compareTo]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.compareTo]
     * </details>
     */
    public operator fun compareTo(other: Integer): Int =
        this.decimal.compareTo(other.decimal)

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
     * SAMPLE: [org.kotools.types.IntegerSample.unaryMinus]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.unaryMinus]
     * </details>
     */
    public operator fun unaryMinus(): Integer {
        if (this == zero()) return this
        val minusSign = "-"
        val isNegative: Boolean = this.decimal.startsWith(minusSign)
        if (isNegative) {
            val text: String = this.decimal.removePrefix(minusSign)
            return fromDecimal(text)
        }
        return fromDecimal("$minusSign${this.decimal}")
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
     * SAMPLE: [org.kotools.types.IntegerSample.plus]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.plus]
     * </details>
     */
    public operator fun plus(other: Integer): Integer {
        val zero: Integer = zero()
        if (this == zero) return other
        if (other == zero) return this
        val sum: String = integerAddition(x = "$this", y = "$other")
        return fromDecimal(sum)
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
     * SAMPLE: [org.kotools.types.IntegerSample.minus]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.minus]
     * </details>
     */
    public operator fun minus(other: Integer): Integer {
        val zero: Integer = zero()
        if (other == zero) return this
        val difference: String = integerSubtraction(x = "$this", y = "$other")
        return fromDecimal(difference)
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
     * SAMPLE: [org.kotools.types.IntegerSample.times]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.times]
     * </details>
     */
    public operator fun times(other: Integer): Integer {
        val zero: Integer = zero()
        if (this == zero || other == zero) return zero
        val one: Integer = from(1)
        if (this == one) return other
        if (other == one) return this
        if (this.isMultipleOfTen()) {
            val suffix: String = this.decimal.removePrefix("1")
            return fromDecimal("$other$suffix")
        }
        if (other.isMultipleOfTen()) {
            val suffix: String = other.decimal.removePrefix("1")
            return fromDecimal("$this$suffix")
        }
        val product: String = integerMultiplication(x = "$this", y = "$other")
        return fromDecimal(product)
    }

    // Future implementation: this % from(10) == zero()
    private fun isMultipleOfTen(): Boolean = this.decimal.startsWith('1')
            && this.decimal.removePrefix("1").all { it == '0' }

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
     * SAMPLE: [org.kotools.types.IntegerSample.div]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.div]
     * </details>
     */
    public operator fun div(other: Integer): Integer {
        val zero: Integer = zero()
        if (other == zero)
            throw ArithmeticException("Integer can't be divided by zero.")
        if (this == zero || other == from(1)) return this
        val quotient: String = integerDivision(x = "$this", y = "$other")
        return fromDecimal(quotient)
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
     * SAMPLE: [org.kotools.types.IntegerSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.decimal
}
