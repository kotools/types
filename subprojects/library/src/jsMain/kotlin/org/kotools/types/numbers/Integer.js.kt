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
public actual fun Integer(text: String): Integer {
    Integer.requirements(text)
    val integer = BigInt(text)
    return JsInteger(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsInteger(private val integer: BigInt) : Integer {
    override fun plus(other: Integer): Integer {
        val sum: BigInt = this.integer + BigInt("$other")
        return JsInteger(sum)
    }

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.plus(other: BigInt): BigInt {
    val x = this
    val y = other
    val sum: dynamic = js("x + y")
    return BigInt("$sum")
}
