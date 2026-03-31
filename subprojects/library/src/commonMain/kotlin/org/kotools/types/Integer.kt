package org.kotools.types

import org.kotools.types.Integer.Companion.from
import org.kotools.types.Integer.Companion.fromDecimal
import org.kotools.types.Integer.Companion.fromDecimalOrNull
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.PlatformInteger
import org.kotools.types.internal.Warning
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
 * SAMPLE: [org.kotools.types.IntegerSample.overflowProblem]
 *
 * **Solution:** The [Integer] type can [add][plus], [subtract][minus] or
 * [multiply][times] integers without producing an overflow.
 *
 * SAMPLE: [org.kotools.types.IntegerSample.overflowSolution]
 *
 * #### Division by zero
 *
 * **Problem:** Performing division and remainder operations by zero on Kotlin
 * integer types have different behavior per platform: throw an
 * [ArithmeticException] on JVM and Native platforms, and return `0` on JS
 * platform.
 *
 * SAMPLE: [org.kotools.types.IntegerJvmNativeSample.divisionByZeroProblem]
 *
 * SAMPLE: [org.kotools.types.IntegerJsSample.divisionByZeroProblem]
 *
 * **Solution:** [Division][div] and [remainder][rem] operations by zero on
 * [Integer] type throw an [ArithmeticException] on all platforms.
 *
 * SAMPLE: [org.kotools.types.IntegerSample.divisionByZeroSolution]
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
 * <br>
 * <details>
 * <summary>
 *     <b>Implementation note</b>
 * </summary>
 *
 * ### Implementation note
 *
 * The [Integer] type uses [String] as its canonical representation to ensure
 * consistent behavior across platforms.
 *
 * For performance, each instance may cache its platform-specific numeric
 * representation after the first use in an arithmetic operation. This avoids
 * repeated parsing when the same instance is involved in multiple operations.
 *
 * This optimization is most effective when instances are reused, and has
 * limited impact on short-lived intermediate results.
 *
 * SAMPLE: [org.kotools.types.IntegerSample.cache]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
public class Integer private constructor(private val decimal: String) {
    private var cached: PlatformInteger? = null

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
            return Integer("$sign$digits")
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
        public fun fromDecimalOrNull(text: String): Integer? = try {
            this.fromDecimal(text)
        } catch (_: IllegalArgumentException) {
            null
        }

        @JvmSynthetic
        internal fun zero(): Integer = this.from(0)

        @JvmSynthetic
        internal fun one(): Integer = this.from(1)
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
        val result: String =
            if (isNegative) this.decimal.removePrefix(minusSign)
            else "$minusSign${this.decimal}"
        return fromDecimal(result)
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
        val x: PlatformInteger = this.toPlatformInteger()
        val y: PlatformInteger = other.toPlatformInteger()
        val sum: PlatformInteger = x + y
        return fromDecimal("$sum")
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
        if (this == zero) return -other
        val x: PlatformInteger = this.toPlatformInteger()
        val y: PlatformInteger = other.toPlatformInteger()
        val difference: PlatformInteger = x - y
        return fromDecimal("$difference")
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
        val one: Integer = one()
        if (this == zero || other == one) return this
        if (other == zero || this == one) return other
        val x: PlatformInteger = this.toPlatformInteger()
        val y: PlatformInteger = other.toPlatformInteger()
        val product: PlatformInteger = x * y
        return fromDecimal("$product")
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
     * SAMPLE: [org.kotools.types.IntegerSample.divOrNull]
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
        val zero: Integer = zero()
        if (other == zero) return null
        if (this == zero || other == one()) return this
        val x: PlatformInteger = this.toPlatformInteger()
        val y: PlatformInteger = other.toPlatformInteger()
        val quotient: PlatformInteger = x / y
        return fromDecimal("$quotient")
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
     * SAMPLE: [org.kotools.types.IntegerSample.remOrNull]
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
        val zero: Integer = zero()
        if (other == zero) return null
        if (this == zero) return this
        if (other == one()) return zero
        val x: PlatformInteger = this.toPlatformInteger()
        val y: PlatformInteger = other.toPlatformInteger()
        val remainder: PlatformInteger = x % y
        return fromDecimal("$remainder")
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
     * <br>
     *
     * See the [toSignedString] function for returning the signed decimal
     * representation of this integer.
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.decimal

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
     * SAMPLE: [org.kotools.types.IntegerSample.toSignedString]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.toSignedString]
     * </details>
     * <br>
     *
     * See the [Integer.toString] function for returning the unsigned decimal
     * representation of this integer.
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toSignedString(): String {
        TODO()
    }

    private fun toPlatformInteger(): PlatformInteger = this.cached
        ?: PlatformInteger(this.decimal).also { this.cached = it }
}
