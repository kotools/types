package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than [zero][Zero].
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
     * and has the same [string representation][PositiveInteger.toString] as
     * this integer.
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
         * Creates an instance of [PositiveInteger] from the specified [number],
         * or returns `null` if the [number] is less than or equal to
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orNullInt]
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
        public fun orNull(number: Int): PositiveInteger? {
            val zero = Zero()
            return if (zero < number) PositiveInteger("$number") else null
        }

        /**
         * Creates an instance of [PositiveInteger] from the specified [number],
         * or returns `null` if the [number] is less than or equal to
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orNullLong]
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
        public fun orNull(number: Long): PositiveInteger? {
            val zero = Zero()
            return if (zero < number) PositiveInteger("$number") else null
        }

        /**
         * Creates an instance of [PositiveInteger] from the specified [text],
         * or returns `null` if the [text] doesn't represent an integer that is
         * greater than [zero][Zero].
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orNullString]
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
        public fun orNull(text: String): PositiveInteger? {
            val number: String = text.removePrefix("+")
            val regex = Regex("""^[1-9]\d*$""")
            return if (number matches regex) PositiveInteger(number) else null
        }

        /**
         * Creates an instance of [PositiveInteger] from the specified [number],
         * or throws an [IllegalArgumentException] if the [number] is less than
         * or equal to [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orThrowInt]
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionJavaSample.orThrowInt]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @JvmStatic
        public fun orThrow(number: Int): PositiveInteger {
            val integer: PositiveInteger? = this.orNull(number)
            return requireNotNull(integer) {
                ExceptionMessage.nonPositive(number)
            }
        }

        /**
         * Creates an instance of [PositiveInteger] from the specified [number],
         * or throws an [IllegalArgumentException] if the [number] is less than
         * or equal to [zero][Zero].
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orThrowLong]
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionJavaSample.orThrowLong]
         * </details>
         * <br>
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [number].
         */
        @JvmStatic
        public fun orThrow(number: Long): PositiveInteger {
            val integer: PositiveInteger? = this.orNull(number)
            return requireNotNull(integer) {
                ExceptionMessage.nonPositive(number)
            }
        }

        /**
         * Creates an instance of [PositiveInteger] from the specified [text],
         * or throws an [IllegalArgumentException] if the [text] doesn't
         * represent an integer that is greater than [zero][Zero].
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orThrowString]
         * </details>
         * <br>
         *
         * This function is not available from Java code due to its non-explicit
         * [support for nullable types](https://kotlinlang.org/docs/java-to-kotlin-nullability-guide.html#support-for-nullable-types).
         *
         * See the [orNull] function for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        @JvmStatic
        public fun orThrow(text: String): PositiveInteger {
            val integer: PositiveInteger? = this.orNull(text)
            return requireNotNull(integer) {
                ExceptionMessage.nonPositiveInteger(text)
            }
        }
    }
}
