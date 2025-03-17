package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmStatic

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than [zero][Zero].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [PositiveInteger.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
public class PositiveInteger private constructor() {
    /** Contains static declarations for the [PositiveInteger] type. */
    public companion object {
        /**
         * Creates an instance of [PositiveInteger] from the specified [number],
         * or throws an [IllegalArgumentException] if the [number] is less than
         * or equals zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionCommonSample.orThrowWithByte]
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
         * SAMPLE: [org.kotools.types.PositiveIntegerCompanionJavaSample.orThrowWithByte]
         * </details>
         */
        @JvmStatic
        public fun orThrow(number: Byte): PositiveInteger {
            val zero = Zero()
            require(zero < number) { ExceptionMessage.nonPositive(number) }
            return PositiveInteger()
        }
    }
}
