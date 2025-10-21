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
public actual fun Integer(number: Long): Integer {
    val integer = BigInt("$number")
    return JsInteger(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsInteger(private val integer: BigInt) : Integer {
    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt
