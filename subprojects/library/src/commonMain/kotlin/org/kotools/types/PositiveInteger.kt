package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmInline

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than zero.
 *
 * Contrarily to the integer types provided by Kotlin ([Byte], [Short], [Int]
 * and [Long]), this type has no maximum value and can hold greater values than
 * [Long.MAX_VALUE].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [PositiveInteger.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
@JvmInline
public value class PositiveInteger private constructor(
    private val text: String
) {
    /** Contains static declarations for the [PositiveInteger] type. */
    public companion object {
        /**
         * Returns a positive integer from the specified [text], or returns
         * `null` if the [text] doesn't represent an integer that is greater
         * than zero.
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.orNullString]
         *
         * The integer represented by the [text] parameter may start with a plus
         * sign (`+`).
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.orNullStringSigned]
         *
         * See the [orThrow] method for throwing an exception instead of
         * returning `null` in case of invalid [text].
         */
        public fun orNull(text: String): PositiveInteger? {
            val number: String = text.removePrefix("+")
            val regex = Regex("""^[1-9]\d*$""")
            return if (number matches regex) PositiveInteger(number) else null
        }

        /**
         * Returns a positive integer from the specified [text], or throws an
         * [IllegalArgumentException] if the [text] doesn't represent an integer
         * that is greater than zero.
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.orThrowString]
         *
         * The integer represented by the [text] parameter may start with a plus
         * sign (`+`).
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.orThrowStringSigned]
         *
         * See the [orNull] method for returning `null` instead of throwing an
         * exception in case of invalid [text].
         */
        public fun orThrow(text: String): PositiveInteger {
            val number: String = text.removePrefix("+")
            val regex = Regex("""^[1-9]\d*$""")
            require(number matches regex) {
                ExceptionMessage.nonPositiveInteger(text)
            }
            return PositiveInteger(number)
        }
    }
}
