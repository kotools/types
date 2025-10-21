package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

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
 * Represents an integer.
 *
 * Contrarily to the Kotlin integer types ([Byte], [Short], [Int] and [Long]),
 * this type has no maximum and minimum values. It can hold greater values than
 * [Long.MAX_VALUE] and lesser values than [Long.MIN_VALUE].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public interface Integer
