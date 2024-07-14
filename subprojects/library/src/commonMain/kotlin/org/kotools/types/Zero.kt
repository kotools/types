package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic

/**
 * Represents the [zero](https://en.wikipedia.org/wiki/0) number.
 *
 * For creating an instance of this type, you can use its constructor or the
 * additional factory functions provided by the [Zero.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public class Zero {
    private val valueAsByte: Byte = 0

    /** Creates an instance of [Zero]. */
    @Suppress("ConvertSecondaryConstructorToPrimary")
    public constructor()

    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [Zero], or returns
     * `false` otherwise.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.equalsOverride]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean = other is Zero

    /**
     * Returns a hash code value for this number.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.hashCodeOverride]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf(this.valueAsByte)

    // ------------------------------ Comparisons ------------------------------

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToByte]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToByte]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Byte): Int =
        this.valueAsByte.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToShort]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToShort]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Short): Int =
        this.valueAsByte.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToInt]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToInt]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Int): Int =
        this.valueAsByte.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToLong]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToLong]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Long): Int =
        this.valueAsByte.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToFloat]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToFloat]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Float): Int =
        this.valueAsByte.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.compareToDouble]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToDouble]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public operator fun compareTo(other: Double): Int =
        this.valueAsByte.compareTo(other)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this number as [Byte].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toByte]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toByte]
     * </details>
     */
    public fun toByte(): Byte = this.valueAsByte

    /**
     * Returns this number as [Short].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toShort]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toShort]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toShort(): Short = this.valueAsByte.toShort()

    /**
     * Returns this number as [Int].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toInt]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toInt]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toInt(): Int = this.valueAsByte.toInt()

    /**
     * Returns this number as [Long].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toLong]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toLong]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toLong(): Long = this.valueAsByte.toLong()

    /**
     * Returns this number as [Float].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toFloat]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toFloat]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toFloat(): Float = this.valueAsByte.toFloat()

    /**
     * Returns this number as [Double].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toDouble]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toDouble]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toDouble(): Double = this.valueAsByte.toDouble()

    /**
     * Returns the character representation of this number.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toChar]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toChar]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toChar(): Char = '0'

    /**
     * Returns the string representation of this number.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.ZeroCommonSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.valueAsByte.toString()

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [Zero] type. */
    public companion object {
        /**
         * The pattern a zero number should match.
         *
         * The underlying value is `^[+-]?0+(?:\.0+)?$`.
         *
         * Here's the explanation associated to each symbol used in this
         * pattern:
         * - `^` **Beginning.** Matches the beginning of the string, or the
         * beginning of a line if the multiline flag (**m**) is enabled.
         * - `[]` **Character set.** Matches any character in the set.
         * - `+` **Character.** Matches a "+" character (char code 43).
         * - `-` **Character.** Matches a "-" character (char code 45).
         * - `?` **Quantifier.** Match between 0 and 1 of the preceding token.
         * - `0` **Character.** Matches a "0" character (char code 48).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `(?:)` **Non-capturing group.** Groups multiple tokens together
         * without creating a capture group.
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (**m**) is enabled.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this property from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.pattern]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this property from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.pattern]
         * </details>
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        public const val PATTERN: String = "^[+-]?0+(?:\\.0+)?\$"

        /**
         * Creates an instance of [Zero] from the string representation of the
         * specified [number], or returns `null` if the string representation of
         * [number] doesn't match the [corresponding pattern][Zero.PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNull]
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
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orNull]
         * </details>
         * <br>
         *
         * You can use the [orThrow] function for throwing an exception instead
         * of returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun orNull(number: Any): Zero? {
            val regex = Regex(this.PATTERN)
            return if ("$number" matches regex) Zero() else null
        }

        /**
         * Creates an instance of [Zero] from the string representation of the
         * specified [number], or throws an [IllegalArgumentException] if the
         * string representation of [number] doesn't match the
         * [corresponding pattern][Zero.PATTERN].
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrow]
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
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrow]
         * </details>
         * <br>
         *
         * You can use the [orNull] function for returning `null` instead of
         * throwing an exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun orThrow(number: Any): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) {
                "'$number' is not a valid representation of zero."
            }
        }
    }
}
