@file:JvmName("Integers")

package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
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
 * SAMPLE: [org.kotools.types.numbers.IntegerSample.constructorLong]
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
 * SAMPLE: [org.kotools.types.numbers.IntegerJavaSample.constructorLong]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmName("from")
public actual fun Integer(number: Long): Integer {
    val integer: BigInteger = BigInteger.valueOf(number)
    return JvmInteger(integer)
}

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
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * The Java static method generated from this function is named `parse` and
 * callable on the `Integers` generated class.
 *
 * Here's an example of calling this function from Java code:
 *
 * SAMPLE: [org.kotools.types.numbers.IntegerJavaSample.constructorString]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmName("parse")
public actual fun Integer(text: String): Integer {
    Integer.requirements(text)
    val integer = BigInteger(text)
    return JvmInteger(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JvmInteger(private val integer: BigInteger) : Integer {
    override fun plus(other: Integer): Integer {
        val sum: BigInteger = this.integer + BigInteger("$other")
        return JvmInteger(sum)
    }

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}
