@file:JvmName("PositiveIntegers")
@file:JvmMultifileClass

package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Returns a positive integer from this number, or throws an
 * [IllegalArgumentException] if this number is less than or equal to zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.PositiveIntegerJsJvmSample.intToPositiveInteger]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * The Java static method generated from this function is named `fromInteger`
 * and can be called on the `PositiveIntegers` generated class.
 *
 * Here's an example of calling this function from Java code:
 *
 * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.intToPositiveInteger]
 * </details>
 * <br>
 *
 * This function is not yet supported on Kotlin/Native.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
@JvmName("fromInteger")
public fun Int.toPositiveInteger(): PositiveInteger = this.toLong()
    .toPositiveInteger()

/**
 * Returns a positive integer from this number, or throws an
 * [IllegalArgumentException] if this number is less than or equal to zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.PositiveIntegerJsJvmSample.longToPositiveInteger]
 * </details>
 * <br>
 *
 * This function is not yet supported on Kotlin/Native.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public expect fun Long.toPositiveInteger(): PositiveInteger

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than zero.
 *
 * Contrarily to the integer types provided by Kotlin ([Byte], [Short], [Int]
 * and [Long]), this type has no maximum value and can hold greater values than
 * [Long.MAX_VALUE].
 *
 * See the [toPositiveInteger] extension function for creating an instance of
 * this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public interface PositiveInteger
