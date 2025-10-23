package org.kotools.types.numbers

import com.ionspin.kotlin.bignum.integer.BigInteger
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
    val integer: BigInteger = BigInteger.fromLong(number)
    return NativeInteger(integer)
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
    val integer: BigInteger = BigInteger.parseString(text)
    return NativeInteger(integer)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class NativeInteger(private val integer: BigInteger) : Integer {
    override fun plus(other: Integer): Integer {
        val sum: BigInteger = this.integer + BigInteger.parseString("$other")
        return NativeInteger(sum)
    }

    override fun minus(other: Integer): Integer {
        val difference: BigInteger =
            this.integer - BigInteger.parseString("$other")
        return NativeInteger(difference)
    }

    override fun equals(other: Any?): Boolean =
        other is NativeInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}
