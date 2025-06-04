package org.kotools.types

import org.kotools.types.PositiveInteger.Companion.orNull
import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is less
 * than [zero][Zero].
 *
 * Contrarily to the integer types provided by Kotlin ([Byte], [Short], [Int]
 * and [Long]), this type has no minimum value and can hold lower values than
 * [Long.MIN_VALUE].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [NegativeInteger.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
public class NegativeInteger private constructor(private val text: String) {
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
     * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.toStringOverride]
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
     * SAMPLE: [org.kotools.types.NegativeIntegerJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.text

    // -------------------------------------------------------------------------

    /** Contains static declarations for the [NegativeInteger] type. */
    public companion object {
        /**
         * Creates an instance of [NegativeInteger] from the specified [number],
         * or returns `null` if the [number] is greater than or equal to
         * [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orNullInt]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @JvmSynthetic
        public fun orNull(number: Int): NegativeInteger? {
            val zero = Zero()
            if (zero <= number) return null
            val text: String = number.toString()
            return NegativeInteger(text)
        }

        /**
         * Creates an instance of [NegativeInteger] from the specified [number],
         * or returns `null` if the [number] is greater than or equal to
         * [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orNullLong]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [number].
         */
        @JvmSynthetic
        public fun orNull(number: Long): NegativeInteger? {
            val zero = Zero()
            if (zero <= number) return null
            val text: String = number.toString()
            return NegativeInteger(text)
        }

        /**
         * Creates an instance of [NegativeInteger] from the specified [text],
         * or returns `null` if the [text] doesn't represent an integer that is
         * less than [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orNullString]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orThrow] function for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        @JvmSynthetic
        public fun orNull(text: String): NegativeInteger? {
            val regex = Regex("""^-[1-9]\d*$""")
            return if (text matches regex) NegativeInteger(text) else null
        }

        /**
         * Creates an instance of [NegativeInteger] from the specified [number],
         * or throws an [IllegalArgumentException] if the [number] is greater
         * than or equal to [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orThrowInt]
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
         * SAMPLE: [org.kotools.types.NegativeIntegerJavaSample.orThrowInt]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @JvmStatic
        public fun orThrow(number: Int): NegativeInteger {
            val integer: NegativeInteger? = this.orNull(number)
            return requireNotNull(integer) {
                ExceptionMessage.nonNegative(number)
            }
        }

        /**
         * Creates an instance of [NegativeInteger] from the specified [number],
         * or throws an [IllegalArgumentException] if the [number] is greater
         * than or equal to [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orThrowLong]
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
         * SAMPLE: [org.kotools.types.NegativeIntegerJavaSample.orThrowLong]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @JvmStatic
        public fun orThrow(number: Long): NegativeInteger {
            val integer: NegativeInteger? = this.orNull(number)
            return requireNotNull(integer) {
                ExceptionMessage.nonNegative(number)
            }
        }

        /**
         * Creates an instance of [NegativeInteger] from the specified [text],
         * or throws an [IllegalArgumentException] if the [text] doesn't
         * represent an integer that is less than [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.NegativeIntegerCommonSample.orThrowString]
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
         * SAMPLE: [org.kotools.types.NegativeIntegerJavaSample.orThrowString]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @JvmStatic
        public fun orThrow(text: String): NegativeInteger {
            val integer: NegativeInteger? = this.orNull(text)
            return requireNotNull(integer) {
                ExceptionMessage.nonNegativeInteger(text)
            }
        }
    }
}
