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
 * - **Instance creation:** constructors, [`parse`][Integer.Companion.parse] and
 * [`parseOrNull`][Integer.Companion.parseOrNull].
 * - **Structural equality operations:** [`equals`][Integer.equals] (`==`) and
 * [`hashCode`][Integer.hashCode].
 * - **Arithmetic operations:** [`plus`][Integer.plus] (`+`),
 * [`minus`][Integer.minus] (`-`) and [`times`][Integer.times] (`*`).
 * - **Conversions:** [`toString`][Integer.toString].
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Evolution ideas</b>
 * </summary>
 *
 * ### Evolution ideas
 *
 * See the following discussions for contributing to the evolution of this type:
 *
 * - [Extension properties for instance creation](https://github.com/kotools/types/discussions/887).
 *
 * If you have an idea that is not listed above, feel free to
 * [open a discussion](https://github.com/kotools/types/discussions/new?category=ideas).
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class Integer private constructor(private val value: PlatformInteger) {
    // ----------------------------- Constructors ------------------------------

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
     * SAMPLE: [org.kotools.types.IntegerSample.constructorWithLong]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.constructorWithLong]
     * </details>
     */
    public constructor(number: Long) : this(PlatformInteger(number))

    /**
     * Creates an instance of [Integer] from the specified [text], or throws an
     * [IllegalArgumentException] if the [text] doesn't represent an integer.
     *
     * The [text] parameter must only contain an optional plus (`+`) or minus
     * (`-`) sign, followed by a sequence of digits.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.IntegerSample.constructorWithDecimalString]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.constructorWithDecimalString]
     * </details>
     */
    public constructor(text: String) : this(PlatformInteger(text))

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
     * SAMPLE: [org.kotools.types.IntegerSample.equalsWithIntegerHavingSameValue]
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
     * SAMPLE: [org.kotools.types.IntegerJavaSample.equalsWithIntegerHavingSameValue]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is Integer && this.value == other.value

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
    final override fun hashCode(): Int = this.value.hashCode()

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
        return Integer(sum)
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
        return Integer(difference)
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
        return Integer(product)
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
    final override fun toString(): String = this.value.toString()

    // ----------------------- Class-level declarations ------------------------

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
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
         * SAMPLE: [org.kotools.types.IntegerSample.parseWithDecimalString]
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
         * SAMPLE: [org.kotools.types.IntegerJavaSample.parseWithDecimalString]
         * </details>
         * <br>
         *
         * See the [parseOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [text].
         */
        @JvmStatic
        public fun parse(text: String): Integer {
            require(text.isNotBlank()) { "Integer should not be blank" }
            val isDecimal: Boolean = text.removePrefix("+")
                .removePrefix("-")
                .all(Char::isDigit)
            require(isDecimal) {
                "Integer can only contain an optional + or - sign, followed " +
                        "by a sequence of digits, was: $text"
            }
            return Integer(PlatformInteger(text))
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
         * SAMPLE: [org.kotools.types.IntegerSample.parseOrNullWithDecimalString]
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
            val isDecimal: Boolean = text.removePrefix("+")
                .removePrefix("-")
                .all(Char::isDigit)
            return if (!isDecimal) null
            else Integer(PlatformInteger(text))
        }
    }
}
