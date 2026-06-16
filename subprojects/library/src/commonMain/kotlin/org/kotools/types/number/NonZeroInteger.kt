package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.HashSeed
import org.kotools.types.internal.errorMessage
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an integer other than zero (ℤ \ {0}).
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Key features</b>
 * </summary>
 *
 * ### Key features
 *
 * - **Creations:** Create from an [Integer], Kotlin integer types, or a string
 * (see [fromInteger], [fromByte], [fromShort], [fromInt], [fromLong], and
 * [parse]).
 * - **Comparisons:** Compare integers using
 * [structural equality][NonZeroInteger.equals] (`x == y`, `x != y`).
 * - **Conversions:** Convert to an [Integer] (see [toInteger]) or its decimal
 * string representation (see [NonZeroInteger.toString]).
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class NonZeroInteger private constructor(private val value: Integer) {
    // --------------------------- Factory functions ---------------------------

    /** Contains class-level declarations for the [NonZeroInteger] type. */
    public companion object {
        /**
         * Returns a [NonZeroInteger] from the specified [value], or throws an
         * [IllegalArgumentException] if [value] represents zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromInteger
         * </details>
         * <br>
         *
         * See the [fromIntegerOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun fromInteger(value: Integer): NonZeroInteger =
            fromIntegerOrNull(value) ?: throw IllegalArgumentException(
                errorMessage("Value must be non-zero", value)
            )

        /**
         * Returns a [NonZeroInteger] from the specified [value], or returns
         * `null` if [value] represents zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromIntegerOrNull
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromInteger] function for throwing an exception instead of
         * returning `null` in case of invalid [value].
         */
        @JvmSynthetic
        public fun fromIntegerOrNull(value: Integer): NonZeroInteger? =
            if (value == Integer.of(0)) null else NonZeroInteger(value)

        /**
         * Returns a [NonZeroInteger] from the specified [value], or throws an
         * [IllegalArgumentException] if [value] is zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromByte
         * </details>
         * <br>
         *
         * See the [fromByteOrNull] function for returning `null` instead of
         * throwing an exception in case of invalid [value].
         */
        @JvmStatic
        public fun fromByte(value: Byte): NonZeroInteger =
            fromByteOrNull(value) ?: throw IllegalArgumentException(
                errorMessage("Value must be non-zero", value)
            )

        /**
         * Returns a [NonZeroInteger] from the specified [value], or returns
         * `null` if [value] is zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.fromByteOrNull
         * </details>
         * <br>
         *
         * This function is hidden from Java, because nullability is not
         * explicit in its type system.
         *
         * See the [fromByte] function for throwing an exception instead of
         * returning `null` in case of invalid [value].
         */
        @JvmSynthetic
        public fun fromByteOrNull(value: Byte): NonZeroInteger? =
            fromIntegerOrNull(Integer.of(value.toLong()))
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is a [NonZeroInteger] representing
     * the same numeric value as this one, or returns `false` otherwise.
     *
     * This function follows the contract of [Any.equals].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is NonZeroInteger && this.value == other.value

    /**
     * Returns a hash code value for this integer.
     *
     * This function follows the contract of [Any.hashCode], with an additional
     * property: if two instances of [NonZeroInteger] are not equal, then
     * calling this function on these objects must produce different hash codes.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.structuralEquality
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int {
        val seed: Int = HashSeed.NonZeroInteger.toInt()
        return 31 * seed + this.value.hashCode()
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns the [Integer] representation of this non-zero integer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.toInteger
     * </details>
     */
    public fun toInteger(): Integer = this.value

    /**
     * Returns the decimal string representation of this non-zero integer.
     *
     * The resulting string is always canonical (no leading plus sign and
     * zeros).
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.number.NonZeroIntegerSample.toStringOverride
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.value.toString()
}
