@file:JvmName("Integers")

package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import java.math.BigInteger

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
 * SAMPLE: [org.kotools.types.IntegerJsJvmSample.constructorLong]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * The Java static method generated from this function is named `from` and
 * callable on the `Integers` generated class.
 *
 * Here's an example of calling this function from Java code:
 *
 * SAMPLE: [org.kotools.types.IntegerJavaSample.constructorLong]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmName("from")
public actual fun Integer(number: Long): Integer {
    val integer: BigInteger = BigInteger.valueOf(number)
    return JvmInteger(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JvmInteger(private val integer: BigInteger) : Integer {
    override fun toString(): String = this.integer.toString()
}
