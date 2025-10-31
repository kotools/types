package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.PlatformInteger
import org.kotools.types.internal.Warning
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
 * - **Instance creation:** [`from`][Integer.Companion.from],
 * [`parse`][Integer.Companion.parse] and
 * [`parseOrNull`][Integer.Companion.parseOrNull].
 * - **Structural equality operations:** [`equals`][Integer.equals] (`==`) and
 * [`hashCode`][Integer.hashCode].
 * - **Arithmetic operations:** [`plus`][Integer.plus] (`+`),
 * [`minus`][Integer.minus] (`-`) and [`times`][Integer.times] (`*`).
 * - **Conversions:** [`toString`][Integer.toString].
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class Integer private constructor(private val decimal: String) {
    private val value: PlatformInteger = PlatformInteger(decimal)

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

    // ------------------------- Arithmetic operations -------------------------

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
        val sum: PlatformInteger = this.value + other.value
        return Integer("$sum")
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
        val difference: PlatformInteger = this.value - other.value
        return Integer("$difference")
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
        val product: PlatformInteger = this.value * other.value
        return Integer("$product")
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
         * SAMPLE: [org.kotools.types.IntegerSample.parse]
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
         * SAMPLE: [org.kotools.types.IntegerJavaSample.parse]
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        public fun parse(text: String): Integer {
            require(text.isNotBlank()) { "Integer should not be blank" }
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

        @JvmSynthetic
        internal fun syntaxErrorIn(text: String): String = "Integer can only " +
                "contain an optional + or - sign, followed by a sequence of " +
                "digits, was: $text"

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
         * SAMPLE: [org.kotools.types.IntegerSample.parseOrNull]
         * </details>
         * <br>
         *
         * This function is not available from Java code, due to its
         * non-explicit [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [parse] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @JvmSynthetic
        public fun parseOrNull(text: String): Integer? {
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
    }
}
