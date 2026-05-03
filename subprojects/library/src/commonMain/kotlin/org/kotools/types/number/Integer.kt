package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.jvm.JvmStatic
import kotlin.math.abs

/**
 * Represents a mathematical integer, with exact arithmetic.
 *
 * Unlike Kotlin's built-in integer types ([Byte], [Short], [Int] and [Long]),
 * the [Integer] type provides exact arithmetic without overflow, and has
 * consistent behavior across all platforms when dividing an integer by zero.
 *
 * Designed to replace primitive integer types where correctness matters, this
 * type intentionally avoids name like `BigInteger` or `BigInt`, as its behavior
 * is part of its contract and not an implementation detail.
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
 * **Problem:** Adding, subtracting or multiplying Kotlin integer types can lead
 * to an overflow, which produces unexpected behavior.
 *
 * SAMPLE: org.kotools.types.number.IntegerSample.overflowProblem
 *
 * **Solution:** The [Integer] type can add, subtract or multiply integers
 * without producing an overflow.
 *
 * TODO: Add Kotlin sample
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
 * **Solution:** Division and remainder operations by zero on [Integer] type
 * throw an [ArithmeticException] on all platforms.
 *
 * TODO: Add Kotlin sample
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class Integer private constructor(
    private val sign: Int,
    private val magnitude: IntArray
) {
    // ------------------------------- Creations -------------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        private val zero: Integer = Integer(sign = 0, magnitude = intArrayOf())

        /**
         * Returns an [Integer] representing exactly the specified [value].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.IntegerSample.ofLong
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
         * SAMPLE: org.kotools.types.IntegerJavaSample.ofLong
         * </details>
         */
        @JvmStatic
        public fun of(value: Long): Integer {
            if (value == 0L) return this.zero
            val sign: Int = if (value > 0) 1 else -1
            val magnitude: IntArray = this.magnitudeOf(value)
            return Integer(sign, magnitude)
        }

        private fun magnitudeOf(value: Long): IntArray {
            if (value == Long.MIN_VALUE) {
                val retain = 1
                val magnitude: IntArray = magnitudeOf(-(value + retain))
                magnitude[0] += retain
                return magnitude
            }
            val base = 1_000_000_000L
            val digits = IntArray(size = 3)
            var number: Long = abs(value)
            var size = 0
            while (number != 0L) {
                val remainder: Int = (number % base).toInt()
                number /= base
                digits[size++] += remainder
            }
            return digits.copyOf(size)
        }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the string representation of this integer.
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
     * SAMPLE: org.kotools.types.IntegerJavaSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String {
        if (this == zero) return "0"
        val builder = StringBuilder()
        if (this.sign < 0) builder.append('-')
        val lastIndex: Int = this.magnitude.lastIndex
        builder.append(this.magnitude[lastIndex])
        for (index in lastIndex - 1 downTo 0) {
            val block: Int = this.magnitude[index]
            val s: String = "$block".padStart(length = 9, padChar = '0')
            builder.append(s)
        }
        return builder.toString()
    }
}
