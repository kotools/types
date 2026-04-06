package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
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
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this decimal floating-point number.
     *
     * The resulting string is normalized by removing insignificant trailing
     * zeros. As a result, calling this function on `Decimal.fromInteger(1)` and
     * `Decimal.fromDecimal("1.0")` produces the same `"1"` result.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * TODO: Add Kotlin code sample.
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
     * TODO: Add Java code sample.
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text
}
