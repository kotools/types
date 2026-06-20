package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import org.kotools.types.number.Decimal.Companion.fromLong
import org.kotools.types.number.Decimal.Companion.parse
import org.kotools.types.number.Decimal.Companion.parseOrNull
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents a mathematical decimal number (ℚ∩ℝ_decimal), with exact
 * arithmetic operations that don't overflow and consistent behavior across all
 * platforms.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Decimal vs Double/Float</b>
 * </summary>
 *
 * ### Decimal vs Double/Float
 *
 * The [Decimal] type is designed to replace floating-point types ([Double] and
 * [Float]) where correctness matters. Floating-point binary representation
 * cannot exactly represent most base-10 decimal values, leading to subtle
 * rounding errors.
 *
 * The [Decimal] type stores values as an exact scaled integer, avoiding all
 * floating-point imprecision.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Why no division?</b>
 * </summary>
 *
 * ### Why no division?
 *
 * The set of terminating decimal numbers is **not** closed under division:
 * dividing two terminating decimals can produce a non-terminating result
 * (e.g., `1 / 3 = 0.333...`). Division is therefore intentionally excluded
 * from this type.
 *
 * This is consistent with how [Integer] excludes operations that would leave
 * the set of integers (square roots, noninteger divisions without remainder).
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
 * - **Creations:** Create from Kotlin integer types or decimal string (see
 * [fromLong] and [parse]).
 * - **Comparisons:** Compare decimals using
 * [structural equality][Decimal.equals] (`x == y`, `x != y`) and
 * [ordering operators][compareTo] (`x < y`, `x <= y`, `x > y`, `x >= y`).
 * - **Arithmetic operations:** [Add][plus] (`x + y`), [subtract][minus]
 * (`x - y`), [multiply][times] (`x * y`), and [negate][unaryMinus] (`-x`)
 * decimals without overflow.
 * - **Conversions:** Convert to its canonical decimal string representation
 * (see [Decimal.toString]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class Decimal private constructor(
    private val unscaledValue: Integer,
    private val scale: Int
) {
    init {
        check(this.scale >= 0) { "Negative decimal scale: ${this.scale}" }
    }

    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Decimal] type. */
    public companion object {
        /**
         * Returns a [Decimal] representing the specified [value].
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
         * SAMPLE: org.kotools.types.number.DecimalSample.fromLong
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
         * SAMPLE: org.kotools.types.number.DecimalJavaSample.fromLong
         * </details>
         */
        @JvmStatic
        public fun fromLong(value: Long): Decimal {
            val unscaled: Integer = Integer.fromLong(value)
            return Decimal(unscaled, scale = 0)
        }

        /**
         * Returns a [Decimal] representing the number described by [value],
         * or throws [NumberFormatException] if [value] doesn't represent a
         * decimal number.
         *
         * A valid decimal representation is defined as:
         *
         * ```
         * decimal = [sign] digit {digit} ['.' digit {digit}]
         * sign    = "+" | "-"
         * digit   = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * _(grammar written in Wirth-style EBNF, where `[]` denotes
         * optionality and `{}` denotes repetition)_
         *
         * Leading zeros, leading plus sign, and trailing fractional zeros are
         * ignored when interpreting [value]. As a result, calling this function
         * with `3.14`, `+03.140`, and `3.14000` produces the same decimal.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.DecimalSample.parsing
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
         * SAMPLE: org.kotools.types.number.DecimalJavaSample.parsing
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun parse(value: String): Decimal {
            if (!value.isDecimal()) {
                val message: String =
                    errorMessage("Invalid decimal representation", value)
                throw NumberFormatException(message)
            }
            return value.toDecimal()
        }

        /**
         * Returns a [Decimal] representing the number described by [value],
         * or returns `null` if [value] doesn't represent a decimal number.
         *
         * A valid decimal representation is defined as:
         *
         * ```
         * decimal = [sign] digit {digit} ['.' digit {digit}]
         * sign    = "+" | "-"
         * digit   = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * _(grammar written in Wirth-style EBNF, where `[]` denotes
         * optionality and `{}` denotes repetition)_
         *
         * Leading zeros, leading plus sign, and trailing fractional zeros are
         * ignored when interpreting [value]. As a result, calling this function
         * with `3.14`, `+03.140`, and `3.14000` produces the same decimal.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.DecimalSample.parsing
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
        public fun parseOrNull(value: String): Decimal? {
            if (!value.isDecimal()) return null
            return value.toDecimal()
        }

        /**
         * Returns `true` if this string represents a decimal number, or returns
         * `false` otherwise.
         *
         * A valid decimal representation is defined as:
         *
         * ```
         * decimal = [sign] digit {digit} ['.' digit {digit}]
         * sign    = "+" | "-"
         * digit   = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
         * ```
         *
         * _(grammar written in Wirth-style EBNF, where `[]` denotes
         * optionality and `{}` denotes repetition)_
         */
        private fun String.isDecimal(): Boolean {
            val unsigned: String = this.removePrefix("+")
                .removePrefix("-")
            if (unsigned.isBlank()) return false

            val dot = '.'
            val digitRange: CharRange = '0'..'9'
            if (dot !in unsigned) return unsigned.all { it in digitRange }

            val integerPart: String = unsigned.substringBefore(dot)
            val fractionalPart: String = unsigned.substringAfter(dot)
            return integerPart.isNotEmpty()
                    && integerPart.all { it in digitRange }
                    && fractionalPart.isNotEmpty()
                    && fractionalPart.all { it in digitRange }
        }

        /**
         * Normalizes this decimal string and returns a [Decimal] from it.
         *
         * The resulting [Decimal] is already canonical - no [Decimal.normalize]
         * needed.
         */
        private fun String.toDecimal(): Decimal {
            val dot = '.'
            if (dot !in this) {
                val unscaled: Integer = Integer.parse(this)
                return Decimal(unscaled, scale = 0)
            }

            val sign: String = if (this.startsWith('-')) "-" else ""
            val unsigned: String = this.removePrefix("+")
                .removePrefix("-")
            val integerPart: String = unsigned.substringBefore(dot)
            val fractionalPart: String = unsigned.substringAfter(dot)
                .trimEnd('0')
            val unscaled: Integer =
                Integer.parse("$sign$integerPart$fractionalPart")

            return Decimal(unscaled, scale = fractionalPart.length)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [Decimal] representing the
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
     * SAMPLE: org.kotools.types.number.DecimalSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean = other is Decimal
            && this.unscaledValue == other.unscaledValue
            && this.scale == other.scale

    /**
     * Returns a hash code value for this decimal.
     *
     * This function follows the contract of [Any.hashCode], with an additional
     * property: if two instances of [Decimal] are not equal, then calling this
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
     * SAMPLE: org.kotools.types.number.DecimalSample.structuralEquality
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.Decimal.toInt()
        return 31 * seed + (31 * this.unscaledValue.hashCode() + this.scale)
    }

    /**
     * Compares this decimal with the [other] one for order.
     *
     * Returns a negative number, zero, or a positive number as this decimal is
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
     * SAMPLE: org.kotools.types.number.DecimalSample.compareTo
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.compareTo
     * </details>
     */
    public operator fun compareTo(other: Decimal): Int {
        val maxScale: Int = maxOf(this.scale, other.scale)
        return this.scaleUpTo(maxScale).compareTo(other.scaleUpTo(maxScale))
    }

    // ------------------------- Arithmetic operations -------------------------

    /**
     * Returns the negative of this decimal.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.DecimalSample.unaryMinus
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.unaryMinus
     * </details>
     */
    public operator fun unaryMinus(): Decimal =
        Decimal(-this.unscaledValue, this.scale)

    /**
     * Adds the [other] decimal to this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.DecimalSample.plus
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.plus
     * </details>
     */
    public operator fun plus(other: Decimal): Decimal {
        val maxScale: Int = maxOf(this.scale, other.scale)
        val sum: Integer = this.scaleUpTo(maxScale) + other.scaleUpTo(maxScale)
        return Decimal(sum, maxScale).normalize()
    }

    /**
     * Subtracts the [other] decimal from this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.DecimalSample.minus
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.minus
     * </details>
     */
    public operator fun minus(other: Decimal): Decimal {
        val maxScale: Int = maxOf(this.scale, other.scale)
        val diff: Integer = this.scaleUpTo(maxScale) - other.scaleUpTo(maxScale)
        return Decimal(diff, maxScale).normalize()
    }

    /**
     * Multiplies this decimal by the [other] one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.DecimalSample.times
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.times
     * </details>
     */
    public operator fun times(other: Decimal): Decimal {
        val product: Integer = this.unscaledValue * other.unscaledValue
        val newScale: Int = this.scale + other.scale
        return Decimal(product, newScale).normalize()
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the canonical decimal string representation of this decimal.
     *
     * The resulting string is always canonical: no leading plus sign, no
     * leading zeros before the decimal point, and no trailing zeros in the
     * fractional part.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.DecimalSample.toStringOverride
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
     * SAMPLE: org.kotools.types.number.DecimalJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String {
        val str: String = this.unscaledValue.toString()
        if (this.scale == 0) return str
        val sign: String = if (str.startsWith('-')) "-" else ""
        val digits: String = str.removePrefix("-")
        return if (digits.length <= this.scale) {
            val pad: String = "0".repeat(this.scale - digits.length)
            "${sign}0.${pad}${digits}"
        } else {
            "${sign}${digits.dropLast(this.scale)}.${digits.takeLast(this.scale)}"
        }
    }

    // ----------------------------- Internals ---------------------------------

    /**
     * Multiplies [unscaledValue] by 10^([targetScale] - [scale]) to align
     * scale.
     */
    private fun scaleUpTo(targetScale: Int): Integer {
        if (this.scale == targetScale) return this.unscaledValue
        val ten: Integer = Integer.fromLong(10)
        var result: Integer = this.unscaledValue
        repeat(targetScale - this.scale) { result *= ten }
        return result
    }

    /**
     * Strips trailing fractional zeros, or returns this decimal number if
     * [scale] is `0`.
     *
     * [Integer.rem] uses Euclidean semantics (`remainder >= 0`), which
     * correctly identifiers divisibility by 10 for negative unscaled values.
     *
     * Example: `-30 % 10 = 0` -> strip -> `Decimal(Integer.fromLong(-3),
     * scale = 0)`
     */
    private fun normalize(): Decimal {
        if (this.scale == 0) return this
        val zero: Integer = Integer.fromLong(0)
        val ten: Integer = Integer.fromLong(10)
        var current: Decimal = this
        while (current.scale > 0) {
            if (current.unscaledValue % ten != zero) break
            val unscaledValue: Integer = current.unscaledValue / ten
            val scale: Int = current.scale - 1
            current = Decimal(unscaledValue, scale)
        }
        return current
    }
}
