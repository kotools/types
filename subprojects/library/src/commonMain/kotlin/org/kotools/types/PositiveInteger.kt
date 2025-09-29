package org.kotools.types

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
 * For creating an instance of this type, see the functions provided by the
 * [PositiveInteger.Companion] type.
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
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.ofString]
         *
         * The integer represented by the [text] may start with a plus sign
         * (`+`).
         *
         * SAMPLE: [org.kotools.types.PositiveIntegerSample.ofStringSigned]
         */
        public infix fun of(text: String): PositiveInteger? {
            val number: String = text.removePrefix("+")
            val regex = Regex("""^[1-9]\d*$""")
            return if (number matches regex) PositiveInteger(number)
            else null
        }
    }
}
