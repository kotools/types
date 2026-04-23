package org.kotools.types

import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

@OptIn(ExperimentalKotoolsTypesApi::class)
internal expect fun Decimal(text: String): Decimal

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
 * SAMPLE: org.kotools.types.DecimalSample.representationProblem
 *
 * **Solution:** The [Decimal] type represents numbers using a base-10 format.
 * Values like `1.1` and `2.2` are stored exactly, ensuring predictable results
 * for arithmetic operations.
 *
 * TODO: Add Kotlin sample.
 *
 * #### Strict arithmetic semantics
 *
 * **Problem:** Kotlin floating-point types allow special values like
 * `Infinity`, and operations such as division by zero do not fail, which can
 * silently propagate invalid results.
 *
 * SAMPLE: org.kotools.types.DecimalSample.divisionByZeroProblem
 *
 * **Solution:** The [Decimal] type follows strict arithmetic rules. Invalid
 * operations, such as division or remainder by zero, throw an
 * [ArithmeticException], making errors explicit and easier to detect.
 *
 * TODO: Add Kotlin sample.
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public interface Decimal {
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
         * SAMPLE: org.kotools.types.DecimalSample.fromIntegerLong
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
         * SAMPLE: org.kotools.types.DecimalJavaSample.fromIntegerLong
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
         * SAMPLE: org.kotools.types.DecimalSample.fromDecimalString
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
         * SAMPLE: org.kotools.types.DecimalJavaSample.fromDecimalString
         * </details>
         */
        @JvmStatic
        public fun fromDecimal(text: String): Decimal {
            require(this.isDecimal(text)) {
                "\"$text\" is not a valid decimal number."
            }
            val normalizedText: String = this.normalize(text)
            return Decimal(normalizedText)
        }

        private fun isDecimal(text: String): Boolean {
            if (text.isBlank()) return false
            val allowedSpecialCharacters: List<Char> = listOf('+', '-', '.')
            val hasAllowedCharacters: Boolean =
                text.all { it.isDigit() || it in allowedSpecialCharacters }
            if (!hasAllowedCharacters) return false
            val radixPointCount: Int = text.count { it == '.' }
            if (radixPointCount > 1) return false
            val unsignedText: String = text.removePrefix("+")
                .removePrefix("-")
            return unsignedText.isNotEmpty()
                    && unsignedText.first().isDigit()
                    && unsignedText.last().isDigit()
        }

        @JvmSynthetic
        internal fun normalize(text: String): String {
            val isZero: Boolean = text.filter(Char::isDigit)
                .all { it == '0' }
            if (isZero) return "0"
            val sign: String = if (text.startsWith('-')) "-" else ""
            val unsignedText: String = text.removePrefix("+")
                .removePrefix("-")
            if ('.' !in unsignedText) return sign + unsignedText.trimStart('0')
            val parts: List<String> = unsignedText.split('.')
            val integerPart: String = parts.first()
                .trimStart('0')
            val fractionalPart: String = parts.last()
                .trimEnd('0')
            return if (fractionalPart.isEmpty()) sign + integerPart
            else "$sign${integerPart}.$fractionalPart"
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an instance of [Decimal], with
     * the same numeric value as this decimal floating-point number, or returns
     * `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.DecimalSample.equality
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
     * SAMPLE: org.kotools.types.DecimalJavaSample.equality
     * </details>
     */
    override fun equals(other: Any?): Boolean

    /**
     * Returns a hash code for this decimal floating-point number.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.DecimalSample.equality
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
     * SAMPLE: org.kotools.types.DecimalJavaSample.equality
     * </details>
     */
    override fun hashCode(): Int

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
     * SAMPLE: org.kotools.types.DecimalSample.toStringOverride
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
     * SAMPLE: org.kotools.types.DecimalJavaSample.toStringOverride
     * </details>
     */
    override fun toString(): String
}
