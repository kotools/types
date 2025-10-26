package org.kotools.types.internal

import java.math.BigInteger

/** Creates an instance of [PlatformInteger] from the specified [number]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer: BigInteger = BigInteger.valueOf(number)
    return JvmInteger(integer)
}

/**
 * Creates an instance of [PlatformInteger] from the specified [text], or throws
 * an [IllegalArgumentException] if the [text] doesn't represent an integer.
 *
 * The [text] parameter must only contain an optional plus (`+`) or minus
 * (`-`) sign, followed by a sequence of digits.
 */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(text: String): PlatformInteger {
    PlatformInteger.requirements(text)
    val integer = BigInteger(text)
    return JvmInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JvmInteger(private val integer: BigInteger) : PlatformInteger {
    override fun unaryMinus(): PlatformInteger = JvmInteger(-this.integer)

    override fun plus(other: PlatformInteger): PlatformInteger {
        val sum: BigInteger = this.integer + BigInteger("$other")
        return JvmInteger(sum)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        val difference: BigInteger = this.integer - BigInteger("$other")
        return JvmInteger(difference)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        val product: BigInteger = this.integer * BigInteger("$other")
        return JvmInteger(product)
    }

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}
