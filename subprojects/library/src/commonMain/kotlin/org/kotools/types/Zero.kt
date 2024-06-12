package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.InvalidZero
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmStatic

private const val FINAL_WARNING: String = "RedundantModalityModifier"

/**
 * Represents the [zero](https://en.wikipedia.org/wiki/0) number.
 *
 * For creating an instance of this type, you can use its constructor or the
 * additional factory functions provided by the [Zero.Companion] type.
 *
 * @constructor Creates an instance of [Zero].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public class Zero {
    private val valueAsByte: Byte = 0

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
     * SAMPLE: ZeroKotlinSample.equalsOverride.md
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
     * SAMPLE: ZeroJavaSample.equalsOverride.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
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
     * SAMPLE: ZeroKotlinSample.hashCodeOverride.md
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
     * SAMPLE: ZeroJavaSample.hashCodeOverride.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
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
     * SAMPLE: ZeroKotlinSample.compareToByte.md
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
     * SAMPLE: ZeroJavaSample.compareToByte.md
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
     * SAMPLE: ZeroKotlinSample.compareToShort.md
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
     * SAMPLE: ZeroJavaSample.compareToShort.md
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
     * SAMPLE: ZeroKotlinSample.compareToInt.md
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
     * SAMPLE: ZeroJavaSample.compareToInt.md
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
     * SAMPLE: ZeroKotlinSample.compareToLong.md
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
     * SAMPLE: ZeroJavaSample.compareToLong.md
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
     * SAMPLE: ZeroKotlinSample.compareToFloat.md
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
     * SAMPLE: ZeroJavaSample.compareToFloat.md
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
     * SAMPLE: ZeroKotlinSample.compareToDouble.md
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
     * SAMPLE: ZeroJavaSample.compareToDouble.md
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
     * SAMPLE: ZeroKotlinSample.toByte.md
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
     * SAMPLE: ZeroJavaSample.toByte.md
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
     * SAMPLE: ZeroKotlinSample.toShort.md
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
     * SAMPLE: ZeroJavaSample.toShort.md
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
     * SAMPLE: ZeroKotlinSample.toInt.md
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
     * SAMPLE: ZeroJavaSample.toInt.md
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
     * SAMPLE: ZeroKotlinSample.toLong.md
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
     * SAMPLE: ZeroJavaSample.toLong.md
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
     * SAMPLE: ZeroKotlinSample.toFloat.md
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
     * SAMPLE: ZeroJavaSample.toFloat.md
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
     * SAMPLE: ZeroKotlinSample.toDouble.md
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
     * SAMPLE: ZeroJavaSample.toDouble.md
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.Unreleased)
    public fun toDouble(): Double = this.valueAsByte.toDouble()

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
     * SAMPLE: ZeroKotlinSample.toStringSample.md
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
     * SAMPLE: ZeroJavaSample.toStringSample.md
     * </details>
     */
    @Suppress(FINAL_WARNING)
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
         * SAMPLE: ZeroCompanionKotlinSample.pattern.md
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
         * SAMPLE: ZeroCompanionJavaSample.pattern.md
         * </details>
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        public const val PATTERN: String = "^[+-]?0+(?:\\.0+)?\$"

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromByte.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromByte.md
         * </details>
         * <br>
         *
         * You can use the [fromByteOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [number].
         */
        @JvmStatic
        public fun fromByte(number: Byte): Zero {
            val zero: Zero? = this.fromByteOrNull(number)
            return requireNotNull(zero) { InvalidZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromByteOrNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromByteOrNull.md
         * </details>
         * <br>
         *
         * You can use the [fromByte] function for throwing an exception instead
         * of returning `null` in case of invalid [number].
         */
        @JvmStatic
        public fun fromByteOrNull(number: Byte): Zero? = Zero()
            .takeIf { it.valueAsByte == number }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromShort.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromShort.md
         * </details>
         * <br>
         *
         * You can use the [fromShortOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromShort(number: Short): Zero {
            val zero: Zero? = this.fromShortOrNull(number)
            return requireNotNull(zero) { InvalidZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromShortOrNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromShortOrNull.md
         * </details>
         * <br>
         *
         * You can use the [fromShort] function for throwing an exception
         * instead of returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromShortOrNull(number: Short): Zero? = Zero()
            .takeIf { it.toShort() == number }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromInt.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromInt.md
         * </details>
         * <br>
         *
         * You can use the [fromIntOrNull] function for returning `null` instead
         * of throwing an exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromInt(number: Int): Zero {
            val zero: Zero? = this.fromIntOrNull(number)
            return requireNotNull(zero) { InvalidZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromIntOrNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromIntOrNull.md
         * </details>
         * <br>
         *
         * You can use the [fromInt] function for throwing an exception instead
         * of returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromIntOrNull(number: Int): Zero? = Zero()
            .takeIf { it.toInt() == number }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromLong.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromLong.md
         * </details>
         * <br>
         *
         * You can use the [fromLongOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromLong(number: Long): Zero {
            val zero: Zero? = this.fromLongOrNull(number)
            return requireNotNull(zero) { InvalidZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromLongOrNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromLongOrNull.md
         * </details>
         * <br>
         *
         * You can use the [fromLong] function for throwing an exception instead
         * of returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromLongOrNull(number: Long): Zero? = Zero()
            .takeIf { it.toLong() == number }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         * For example, calling this function with `0.1` will throw an exception
         * because it is not strictly equal to `0`.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromFloat.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromFloat.md
         * </details>
         * <br>
         *
         * You can use the [fromFloatOrNull] function for returning `null`
         * instead of throwing an exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromFloat(number: Float): Zero {
            val zero: Zero? = this.fromFloatOrNull(number)
            return requireNotNull(zero) { InvalidZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         * For example, calling this function with `0.1` will return `null`
         * because it is not strictly equal to `0`.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ZeroCompanionKotlinSample.fromFloatOrNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.fromFloatOrNull.md
         * </details>
         * <br>
         *
         * You can use the [fromFloat] function for throwing an exception
         * instead of returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmStatic
        public fun fromFloatOrNull(number: Float): Zero? = Zero()
            .takeIf { it.toFloat() == number }

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
         * SAMPLE: ZeroCompanionKotlinSample.orNull.md
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
         * SAMPLE: ZeroCompanionJavaSample.orNull.md
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
         * SAMPLE: ZeroCompanionKotlinSample.orThrow.md
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
         * SAMPLE: ZeroCompanionJavaSample.orThrow.md
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
