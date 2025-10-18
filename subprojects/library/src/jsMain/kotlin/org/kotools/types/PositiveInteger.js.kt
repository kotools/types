package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

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
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public actual fun Long.toPositiveInteger(): PositiveInteger {
    require(this > 0) { "$this must be greater than zero." }
    val integer = BigInt("$this")
    return PositiveIntegerJs(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private value class PositiveIntegerJs(
    private val integer: BigInt
) : PositiveInteger {
    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(number: String): BigInt

private external object BigInt
