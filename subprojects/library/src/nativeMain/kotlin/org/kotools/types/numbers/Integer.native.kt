package org.kotools.types.numbers

import com.ionspin.kotlin.bignum.integer.BigInteger

internal actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer: BigInteger = BigInteger.fromLong(number)
    return NativeInteger(integer)
}

internal actual fun PlatformInteger(text: String): PlatformInteger {
    PlatformInteger.requirements(text)
    val integer: BigInteger = BigInteger.parseString(text)
    return NativeInteger(integer)
}

private class NativeInteger(private val integer: BigInteger) : PlatformInteger {
    override fun plus(other: PlatformInteger): PlatformInteger {
        val sum: BigInteger = this.integer + BigInteger.parseString("$other")
        return NativeInteger(sum)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        val difference: BigInteger =
            this.integer - BigInteger.parseString("$other")
        return NativeInteger(difference)
    }

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
