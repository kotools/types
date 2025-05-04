package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
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
public class PositiveInteger private constructor() {
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
         */
        @JvmSynthetic
        public fun orNull(number: Int): PositiveInteger? {
            val zero = Zero()
            return if (zero < number) PositiveInteger() else null
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
         */
        @JvmSynthetic
        public fun orNull(number: Long): PositiveInteger? {
            val zero = Zero()
            return if (zero < number) PositiveInteger() else null
        }
    }
}
