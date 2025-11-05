package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import org.kotools.types.internal.integerAddition
import org.kotools.types.internal.integerDivision
import org.kotools.types.internal.integerMultiplication
import org.kotools.types.internal.integerRemainder
import org.kotools.types.internal.integerSubtraction
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an integer.
 *
 * Use this type for preventing overflow when adding, subtracting or multiplying
 * integers.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Overflow
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
 *     <b>Declarations</b>
 * </summary>
 *
 * ### Declarations
 *
 * - **Instance creation:** [from][Integer.Companion.from],
 * [fromDecimal][Integer.Companion.fromDecimal] and
 * [fromDecimalOrNull][Integer.Companion.fromDecimalOrNull].
 * - **Structural equality operations:** [equals][Integer.equals] (`x == y`,
 * `x != y`) and [hashCode][Integer.hashCode].
 * - **Comparisons:** [compareTo][Integer.compareTo] (`x > y`, `x >= y`,
 * `x < y`, `x <= y`).
 * - **Arithmetic operations:** [unaryMinus][Integer.unaryMinus] (`-x`),
 * [plus][Integer.plus] (`x + y`), [minus][Integer.minus] (`x - y`),
 * [times][Integer.times] (`x * y`), [div][Integer.div] (`x / y`) and
 * [rem][Integer.rem] (`x % y`).
 * - **Conversions:** [toString][Integer.toString].
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
public class Integer private constructor(
    private val decimal: String
) : Comparable<Integer> {
    // -------------------- Structural equality operations ---------------------

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

    // ------------------------------ Comparisons ------------------------------

    /**
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
    @Suppress(Warning.FINAL)
    final override fun compareTo(other: Integer): Int =
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
        if (this == Zero) return this
        val isNegative: Boolean = this.decimal.startsWith('-')
        if (isNegative) {
            val text: String = this.decimal.removePrefix("-")
            return fromDecimal(text)
        }
        val text = "-${this.decimal}"
        return fromDecimal(text)
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
        val zero: Integer = Zero
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
        val zero: Integer = Zero
        if (this == zero) return -other
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
        val zero: Integer = Zero
        if (this == zero || other == zero) return zero
        val one: Integer = One
        if (this == one) return other
        if (other == one) return this
        val product: String = integerMultiplication(x = "$this", y = "$other")
        return fromDecimal(product)
    }

    /**
     * Returns the quotient of dividing this integer by the [other] one, or
     * throws an [IllegalArgumentException] if the [other] integer is zero.
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
        val zero: Integer = Zero
        require(other != zero, ::divisionByZeroError)
        if (this == zero || other == One) return this
        val quotient: String = integerDivision(x = "$this", y = "$other")
        return fromDecimal(quotient)
    }

    /**
     * Returns the remainder of dividing this integer by the [other] one, or
     * throws an [IllegalArgumentException] if the [other] integer is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.IntegerSample.rem]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.rem]
     * </details>
     */
    public operator fun rem(other: Integer): Integer {
        val zero: Integer = Zero
        require(other != zero, ::divisionByZeroError)
        if (this == zero || other == One) return zero
        val remainder: String = integerRemainder(x = "$this", y = "$other")
        return fromDecimal(remainder)
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the programming-level string representation of this integer.
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

    // ----------------------- Class-level declarations ------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        @get:JvmSynthetic
        internal val Zero: Integer get() = this.from(0)

        @get:JvmSynthetic
        internal val One: Integer get() = this.from(1)

        /**
         * Creates an instance of [Integer] from the specified [number].
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
         * Creates an instance of [Integer] from the specified [text], or throws
         * an [IllegalArgumentException] if the [text] doesn't represent an
         * integer.
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
            require(text.isNotBlank(), this::blankError)
            val textWithoutPlusSignPrefix: String = text.removePrefix("+")
            val unsignedText: String =
                textWithoutPlusSignPrefix.removePrefix("-")
            val isDecimal: Boolean =
                unsignedText.isNotEmpty() && unsignedText.all(Char::isDigit)
            require(isDecimal) { this.syntaxErrorIn(text) }
            val isZero: Boolean = unsignedText.all { it == '0' }
            if (isZero) return Zero
            val sign: String =
                if (textWithoutPlusSignPrefix.startsWith('-')) "-"
                else ""
            val digits: String = unsignedText.trimStart('0')
            return Integer(decimal = "$sign$digits")
        }

        /**
         * Creates an instance of [Integer] from the specified [text], or
         * returns `null` if the [text] doesn't represent an integer.
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
            if (isZero) return Zero
            val sign: String =
                if (textWithoutPlusSignPrefix.startsWith('-')) "-"
                else ""
            val digits: String = unsignedText.trimStart('0')
            return Integer(decimal = "$sign$digits")
        }

        @JvmSynthetic
        internal fun blankError(): String = "Integer can't be blank."

        @JvmSynthetic
        internal fun divisionByZeroError(): String =
            "Integer can't be divided by zero."

        @JvmSynthetic
        internal fun syntaxErrorIn(text: String): String = "Integer can only " +
                "contain an optional + or - sign, followed by a sequence of " +
                "digits (was: $text)."
    }
}
