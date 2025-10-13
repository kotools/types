package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic
import kotlin.math.max

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than zero.
 *
 * Contrarily to the integer types provided by Kotlin ([Byte], [Short], [Int]
 * and [Long]), this type has no maximum value and can hold greater values than
 * [Long.MAX_VALUE].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [PositiveInteger.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public class PositiveInteger private constructor(private val text: String) {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [PositiveInteger]
     * with the same [string representation][PositiveInteger.toString], or
     * returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.equalsOverride]
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
     * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is PositiveInteger && this.text == other.text

    /**
     * Returns a hash code value for this integer, using its
     * [string representation][PositiveInteger.toString].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.hashCodeOverride]
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
     * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf(this.text)

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
     * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.plus]
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
     * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.plus]
     * </details>
     */
    public operator fun plus(other: PositiveInteger): PositiveInteger {
        val length: Int = max(this.text.length, other.text.length)
        val firstDigits: List<Int> = this.text.map(Char::digitToInt)
            .reversed()
        val secondDigits: List<Int> = other.text.map(Char::digitToInt)
            .reversed()
        var carry = 0
        val result: String = 1.rangeTo(length)
            .map {
                val index: Int = it - 1
                val x: Int = firstDigits.getOrNull(index) ?: 0
                val y: Int = secondDigits.getOrNull(index) ?: 0
                val sum: Int = x + y + carry
                if (it == length) return@map sum
                carry = sum / 10
                sum % 10
            }
            .reversed()
            .joinToString(separator = "")
        return checkNotNull(PositiveInteger of result) {
            "$this + $other = $result, which is not a positive integer."
        }
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
     * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.times]
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
     * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.times]
     * </details>
     */
    public operator fun times(other: PositiveInteger): PositiveInteger {
        val one: PositiveInteger = minimum()
        if (other == one) return this
        if (this == one) return other
        val multiplierDigits: List<Int> = this.text.map(Char::digitToInt)
            .reversed()
        return other.text.map(Char::digitToInt)
            .reversed()
            .mapIndexed { yIndex: Int, y: Int ->
                var carry = 0
                val trailingZeros: List<Int> = buildList {
                    repeat(yIndex) { this += 0 }
                }
                multiplierDigits
                    .mapIndexed { index: Int, x: Int ->
                        val product: Int = x * y + carry
                        if (index == multiplierDigits.size - 1) product
                        else {
                            carry = product / 10
                            product % 10
                        }
                    }
                    .reversed()
                    .plus(trailingZeros)
                    .joinToString(separator = "")
            }
            .mapNotNull(::of)
            .reduce { x: PositiveInteger, y: PositiveInteger -> x + y }
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
     * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [PositiveInteger] type. */
    public companion object {
        /**
         * Returns a positive integer with the specified [text], or returns
         * `null` if the [text] doesn't represent an integer that is greater
         * than zero.
         *
         * The integer represented by the [text] parameter may start with a plus
         * sign (`+`).
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.of]
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
         * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.of]
         * </details>
         */
        @JvmStatic
        public infix fun of(text: String): PositiveInteger? {
            val number: String = text.removePrefix("+")
            val regex = Regex("""^[1-9]\d*$""")
            return if (number matches regex) PositiveInteger(text = number)
            else null
        }

        /**
         * Returns the minimum value that a positive integer can have.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerCommonSample.minimum]
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
         * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.minimum]
         * </details>
         */
        @JvmStatic
        public fun minimum(): PositiveInteger {
            val text = "1"
            return checkNotNull(this of text) {
                "'$text' doesn't represent an integer greater than zero."
            }
        }
    }
}
