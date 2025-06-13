package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents the [zero](https://en.wikipedia.org/wiki/0) number.
 *
 * For creating an instance of this type, see its constructor or the additional
 * factory functions provided by the [Zero.Companion] type.
 *
 * @constructor Creates an instance of [Zero].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public class Zero {
    private val value: Byte = 0

    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of [Zero], or returns
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
     * <details>
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
    final override fun hashCode(): Int = hashCodeOf(this.value)

    // ------------------------------ Comparisons ------------------------------

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Byte): Int =
        this.value.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Short): Int =
        this.value.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Int): Int = this.value.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Long): Int =
        this.value.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Float): Int =
        this.value.compareTo(other)

    /**
     * Compares this number with the [other] one for order.
     * Returns zero if this number equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Double): Int =
        this.value.compareTo(other)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this number as [Byte].
     *
     * <br>
     * <details>
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
    public fun toByte(): Byte = this.value

    /**
     * Returns this number as [Short].
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toShort(): Short = this.value.toShort()

    /**
     * Returns this number as [Int].
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toInt(): Int = this.value.toInt()

    /**
     * Returns this number as [Long].
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toLong(): Long = this.value.toLong()

    /**
     * Returns this number as [Float].
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toFloat(): Float = this.value.toFloat()

    /**
     * Returns this number as [Double].
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toDouble(): Double = this.value.toDouble()

    /**
     * Returns the character representation of this number.
     *
     * <br>
     * <details>
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
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toChar(): Char = '0'

    /**
     * Returns the string representation of this number.
     *
     * <br>
     * <details>
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
    final override fun toString(): String = this.value.toString()

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [Zero] type. */
    public companion object {
        // ------------------------- Factory functions -------------------------

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNull]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmSynthetic
        public fun orNull(number: Byte): Zero? {
            val expected: Byte = 0
            return if (number == expected) Zero() else null
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithShort]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Short): Zero? {
            val expected: Short = 0
            return if (number == expected) Zero() else null
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithInt]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Int): Zero? =
            if (number == 0) Zero() else null

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithLong]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Long): Zero? =
            if (number == 0L) Zero() else null

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithFloat]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Float): Zero? =
            if (number == 0f) Zero() else null

        /**
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithDouble]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Double): Zero? =
            if (number == 0.0) Zero() else null

        /**
         * Creates an instance of [Zero] from the specified [text], or returns
         * `null` if the [text] is an invalid representation of zero.
         *
         * The [text] is a valid representation if it matches the following
         * pattern: `^0+(?:\.0+)?$`.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Pattern symbols</b>
         * </summary>
         *
         * Here's the explanation associated to each symbol used in this
         * pattern:
         * - `^` **Beginning.** Matches the beginning of the string, or the
         * beginning of a line if the multiline flag (**m**) is enabled.
         * - `0` **Character.** Matches a "0" character (char code 48).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `(?:)` **Non-capturing group.** Groups multiple tokens together
         * without creating a capture group.
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `?` **Quantifier.** Match between 0 and 1 of the preceding token.
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (**m**) is enabled.
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orNullWithString]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(text: String): Zero? {
            val regex = Regex("""^0+(?:\.0+)?$""")
            return if (text matches regex) Zero() else null
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithByte]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithByte]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmStatic
        public fun orThrow(number: Byte): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithShort]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithShort]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Short): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithInt]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithInt]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Int): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithLong]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithLong]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Long): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithFloat]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithFloat]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Float): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [number], or throws
         * an [IllegalArgumentException] if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithDouble]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithDouble]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Double): Zero {
            val zero: Zero? = this.orNull(number)
            return requireNotNull(zero) { ExceptionMessage.nonZero(number) }
        }

        /**
         * Creates an instance of [Zero] from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] is an invalid representation
         * of zero.
         *
         * The [text] is a valid representation if it matches the following
         * pattern: `^0+(?:\.0+)?$`.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Pattern symbols</b>
         * </summary>
         *
         * Here's the explanation associated to each symbol used in this
         * pattern:
         * - `^` **Beginning.** Matches the beginning of the string, or the
         * beginning of a line if the multiline flag (**m**) is enabled.
         * - `0` **Character.** Matches a "0" character (char code 48).
         * - `+` **Quantifier.** Match 1 or more of the preceding token.
         * - `(?:)` **Non-capturing group.** Groups multiple tokens together
         * without creating a capture group.
         * - `\.` **Escaped character.** Matches a "." character (char code 46).
         * - `?` **Quantifier.** Match between 0 and 1 of the preceding token.
         * - `$` **End.** Matches the end of the string, or the end of a line if
         * the multiline flag (**m**) is enabled.
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCommonSample.orThrowWithString]
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
         * SAMPLE: [org.kotools.types.ZeroJavaSample.orThrowWithString]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(text: String): Zero {
            val zero: Zero? = this.orNull(text)
            return requireNotNull(zero) { ExceptionMessage.nonZero(text) }
        }
    }
}
