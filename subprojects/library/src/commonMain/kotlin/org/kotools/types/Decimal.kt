package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmStatic

/**
 * Represents a decimal floating-point number with exact base-10 representation.
 *
 * Use this type when correctness matters (e.g., financial calculations, tax
 * computations), and when binary floating-point types ([Float] and [Double])
 * are not suitable.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### Exact decimal representation
 *
 * **Problem:** Kotlin floating-point types ([Float] and [Double]) use binary
 * representation (IEEE 754 binary32 and binary64). As a result, many decimal
 * numbers (like `1.1` or `2.2`) cannot be represented exactly, leading to
 * surprising results in arithmetic operations.
 *
 * SAMPLE: [org.kotools.types.DecimalSample.representationProblem]
 *
 * **Solution:** The [Decimal] type represents numbers using a base-10 format.
 * Values like `1.1` and `2.2` are stored exactly, ensuring predictable results
 * for arithmetic operations.
 *
 * TODO: Add Kotlin sample.
 *
 * #### Controlled precision
 *
 * **Problem:** [Float] and [Double] provide fixed precision defined by IEEE 754
 * binary formats. They are not suitable for domains requiring strict decimal
 * rounding rules, such as financial or tax computations.
 *
 * SAMPLE: [org.kotools.types.DecimalSample.precisionProblem]
 *
 * **Solution:** The [Decimal] type supports arbitrary precision by default,
 * allowing computations without premature rounding. When needed, precision can
 * be explicitly controlled (e.g., decimal32, decimal64, decimal128) to match
 * domain-specific requirements.
 *
 * TODO: Add Kotlin sample.
 *
 * #### Strict arithmetic semantics
 *
 * **Problem:** Kotlin floating-point types allow special values like
 * `Infinity`, and operations such as division by zero do not fail, which can
 * silently propagate invalid results.
 *
 * SAMPLE: [org.kotools.types.DecimalSample.divisionByZeroProblem]
 *
 * **Solution:** The [Decimal] type follows strict arithmetic rules. Invalid
 * operations, such as division or remainder by zero, throw an
 * [ArithmeticException], making errors explicit and easier to detect.
 *
 * TODO: Add Kotlin sample.
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class Decimal private constructor(private val text: String) {
    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Decimal] type. */
    public companion object {
        /**
         * Creates a [Decimal] from the specified [number].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.DecimalSample.fromIntegerLong]
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
         * SAMPLE: [org.kotools.types.DecimalJavaSample.fromIntegerLong]
         * </details>
         */
        @JvmStatic
        public fun fromInteger(number: Long): Decimal = Decimal("$number")

        /**
         * Creates a [Decimal] from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't represent a
         * floating-point number.
         *
         * The [text] parameter must only contain an optional plus sign (`+`) or
         * minus sign (`-`), followed by a sequence of digits, then by an
         * optional fractional part consisting of a radix point (`.`) and
         * another sequence of digits (e.g., `1234`, `+1.234`, `-12.34`).
         *
         * Also, the [text] parameter is normalized by removing insignificant
         * leading and trailing zeros. As a result, calling this function with
         * `1`, `1.0` and `01` produces the same result.
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
         * SAMPLE: [org.kotools.types.DecimalSample.fromDecimalString]
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
         * SAMPLE: [org.kotools.types.DecimalJavaSample.fromDecimalString]
         * </details>
         */
        @JvmStatic
        public fun fromDecimal(text: String): Decimal {
            require(text.isNotBlank()) {
                "Decimal floating-point number can't be blank."
            }
            val plusSign = '+'
            require(text != "$plusSign") {
                "Plus sign ($plusSign) is not a decimal floating-point number."
            }
            val minusSign = '-'
            require(text != "$minusSign") {
                "Minus sign ($minusSign) is not a decimal floating-point " +
                        "number."
            }
            val radixPoint = '.'
            require(text != "$radixPoint") {
                "Radix point ($radixPoint) is not a decimal floating-point " +
                        "number."
            }
            val unsignedText: String = text.removePrefix("$plusSign")
                .removePrefix("$minusSign")
            val isDecimalText: Boolean =
                unsignedText.all { it.isDigit() || it == radixPoint }
            require(isDecimalText) {
                "Only digits, plus sign ($plusSign), minus sign " +
                        "($minusSign), and radix point ($radixPoint) " +
                        "characters are allowed in decimal floating-point " +
                        "number (was: $text)."
            }
            val radixPointCount: Int = text.count { it == radixPoint }
            require(radixPointCount < 2) {
                "Decimal floating-point number can't have multiple radix " +
                        "points (was: $text)."
            }
            if (radixPoint in text) {
                val integerPartIsWellFormed: Boolean = unsignedText.first()
                    .isDigit()
                require(integerPartIsWellFormed) {
                    "Integer part of decimal floating-point number is " +
                            "malformed (was: $text)."
                }
                val fractionalPartIsWellFormed: Boolean = unsignedText.last()
                    .isDigit()
                require(fractionalPartIsWellFormed) {
                    "Fractional part of decimal floating-point number is " +
                            "malformed (was: $text)."
                }
            }
            val zero = '0'
            val isZero: Boolean =
                unsignedText.all { it == zero || it == radixPoint }
            if (isZero) return Decimal("$zero")
            val sign: String =
                if (text.startsWith(minusSign)) "-"
                else ""
            if (radixPoint !in unsignedText) {
                val integerPart: String = unsignedText.trimStart(zero)
                return Decimal("$sign$integerPart")
            }
            val parts: List<String> = unsignedText.split(radixPoint)
            val integerPart: String = parts.first()
                .trimStart(zero)
            val fractionalPart: String = parts.last()
                .trimEnd(zero)
            return if (fractionalPart.isEmpty()) Decimal("$sign$integerPart")
            else Decimal("$sign${integerPart}.$fractionalPart")
        }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this decimal floating-point number.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.DecimalSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.DecimalJavaSample.toStringOverride]
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.text
}
