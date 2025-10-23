package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Creates an instance of [Integer] from the specified [number].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.numbers.IntegerSample.constructorLong]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public expect fun Integer(number: Long): Integer

/**
 * Creates an instance of [Integer] from the specified [text], or throws an
 * [IllegalArgumentException] if the [text] doesn't represent an integer.
 *
 * The [text] parameter must only contain an optional plus (`+`) or minus (`-`)
 * sign, followed by a sequence of digits.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.numbers.IntegerSample.constructorString]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public expect fun Integer(text: String): Integer

/**
 * Represents an integer.
 *
 * Contrarily to the Kotlin integer types ([Byte], [Short], [Int] and [Long]),
 * this type has no maximum and minimum values. It can hold greater values than
 * [Long.MAX_VALUE] and lesser values than [Long.MIN_VALUE].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public interface Integer {
    /**
     * Adds the [other] integer to this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.numbers.IntegerSample.plus]
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
     * SAMPLE: [org.kotools.types.numbers.IntegerJavaSample.plus]
     * </details>
     */
    public operator fun plus(other: Integer): Integer

    /**
     * Subtracts the [other] integer from this one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.numbers.IntegerSample.minus]
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
     * SAMPLE: [org.kotools.types.numbers.IntegerJavaSample.minus]
     * </details>
     */
    public operator fun minus(other: Integer): Integer

    /**
     * Multiplies this integer by the [other] one.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.numbers.IntegerSample.times]
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
     * SAMPLE: [org.kotools.types.numbers.IntegerJavaSample.times]
     * </details>
     */
    public operator fun times(other: Integer): Integer

    /** Contains class-level declarations for the [Integer] type. */
    public companion object {
        @JvmSynthetic
        internal fun requirements(text: String) {
            require(text.isNotBlank()) { "Integer should not be blank" }
            val isNumericText: Boolean = text.removePrefix("+")
                .removePrefix("-")
                .all(Char::isDigit)
            require(isNumericText) {
                "Integer can only contain an optional + or - sign, followed " +
                        "by a sequence of digits, was: $text"
            }
        }
    }
}
