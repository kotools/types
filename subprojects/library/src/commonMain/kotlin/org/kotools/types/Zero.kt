package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ErrorMessage
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
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.ZeroCommonSample.primaryConstructor]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Java code:
 *
 * SAMPLE: [org.kotools.types.ZeroJavaSample.primaryConstructor]
 * </details>
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
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToByte]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Byte): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToShort]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Short): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToInt]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Int): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToLong]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Long): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToFloat]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Float): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.compareToDouble]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public operator fun compareTo(other: Double): Int =
        this.valueAsByte.compareTo(other)

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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toByte]
     * </details>
     */
    public fun toByte(): Byte = this.valueAsByte

    /**
     * Returns this number as [Short].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toShort]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toShort(): Short = this.valueAsByte.toShort()

    /**
     * Returns this number as [Int].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toInt]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toInt(): Int = this.valueAsByte.toInt()

    /**
     * Returns this number as [Long].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toLong]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toLong(): Long = this.valueAsByte.toLong()

    /**
     * Returns this number as [Float].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toFloat]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toFloat(): Float = this.valueAsByte.toFloat()

    /**
     * Returns this number as [Double].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
     *
     * SAMPLE: [org.kotools.types.ZeroJavaSample.toDouble]
     * </details>
     */
    @ExperimentalSince(KotoolsTypesVersion.V4_5_2)
    public fun toDouble(): Double = this.valueAsByte.toDouble()

    /**
     * Returns the character representation of this number.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
     * Here's an example of calling this method from Kotlin code:
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
     * Here's an example of calling this method from Java code:
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
         * Creates an instance of [Zero] from the specified [number], or returns
         * `null` if the [number] is other than zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNull]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmSynthetic
        public fun orNull(number: Byte): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithShort]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Short): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithInt]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Int): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithLong]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Long): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithFloat]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Float): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithDouble]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(number: Double): Zero? = try {
            this.orThrow(number)
        } catch (exception: IllegalArgumentException) {
            null
        }

        /**
         * Creates an instance of [Zero] from the specified [text], or returns
         * `null` if the [text] is an invalid representation of zero.
         *
         * The [text] is a valid representation if it matches the following
         * regular expression: [`^0+(?:\.0+)?$`](https://regexr.com/8arpu).
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orNullWithString]
         * </details>
         * <br>
         *
         * This method is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmSynthetic
        public fun orNull(text: String): Zero? = try {
            this.orThrow(text)
        } catch (exception: IllegalArgumentException) {
            null
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithByte]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithByte]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V4_5_3)
        @JvmStatic
        public fun orThrow(number: Byte): Zero {
            val expected: Byte = 0
            require(number == expected) { ErrorMessage.invalidZero(number) }
            return Zero()
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithShort]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithShort]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Short): Zero {
            val expected: Short = 0
            require(number == expected) { ErrorMessage.invalidZero(number) }
            return Zero()
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithInt]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithInt]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Int): Zero {
            require(number == 0) { ErrorMessage.invalidZero(number) }
            return Zero()
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithLong]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithLong]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Long): Zero {
            require(number == 0L) { ErrorMessage.invalidZero(number) }
            return Zero()
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithFloat]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithFloat]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Float): Zero {
            require(number == 0f) { ErrorMessage.invalidZero(number) }
            return Zero()
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
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithDouble]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithDouble]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(number: Double): Zero {
            require(number == 0.0) { ErrorMessage.invalidZero(number) }
            return Zero()
        }

        /**
         * Creates an instance of [Zero] from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] is an invalid representation
         * of zero.
         *
         * The [text] is a valid representation if it matches the following
         * regular expression: [`^0+(?:\.0+)?$`](https://regexr.com/8arpu).
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this method from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionCommonSample.orThrowWithString]
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this method from Java code:
         *
         * SAMPLE: [org.kotools.types.ZeroCompanionJavaSample.orThrowWithString]
         * </details>
         * <br>
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @ExperimentalSince(KotoolsTypesVersion.V5_0_0)
        @JvmStatic
        public fun orThrow(text: String): Zero {
            val regex = Regex("""^0+(?:\.0+)?$""")
            require(text matches regex) {
                "'$text' is not a valid representation of zero."
            }
            return Zero()
        }
    }
}
