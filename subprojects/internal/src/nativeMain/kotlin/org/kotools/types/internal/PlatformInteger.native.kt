package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

/** Creates an instance of [PlatformInteger] from the specified [number]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer: BigInteger = BigInteger.fromLong(number)
    return NativeInteger(integer)
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
    val integer: BigInteger = BigInteger.parseString(text)
    return NativeInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
private class NativeInteger(private val integer: BigInteger) : PlatformInteger {
    override fun times(other: PlatformInteger): PlatformInteger {
        val product: BigInteger =
            this.integer * BigInteger.parseString("$other")
        return NativeInteger(product)
    }

    override fun equals(other: Any?): Boolean =
        other is NativeInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}
