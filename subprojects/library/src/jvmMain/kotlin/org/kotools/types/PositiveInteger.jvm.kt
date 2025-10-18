@file:JvmName("PositiveIntegers")

package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import java.math.BigInteger

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
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * The `from` Java static method generated from this function is provided by the
 * `PositiveIntegers` generated class.
 *
 * Here's an example of calling this function from Java code:
 *
 * SAMPLE: [org.kotools.types.PositiveIntegerJavaSample.longToPositiveInteger]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
@JvmName("from")
public actual fun Long.toPositiveInteger(): PositiveInteger {
    require(this > 0) { "$this must be greater than zero." }
    val integer: BigInteger = BigInteger.valueOf(this)
    return PositiveIntegerJvm(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
@JvmInline
private value class PositiveIntegerJvm(
    private val integer: BigInteger
) : PositiveInteger {
    override fun toString(): String = this.integer.toString()
}
