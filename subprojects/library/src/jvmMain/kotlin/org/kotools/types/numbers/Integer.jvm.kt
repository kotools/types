package org.kotools.types.numbers

import java.math.BigInteger

internal actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer: BigInteger = BigInteger.valueOf(number)
    return JvmInteger(integer)
}

internal actual fun PlatformInteger(text: String): PlatformInteger {
    PlatformInteger.requirements(text)
    val integer = BigInteger(text)
    return JvmInteger(integer)
}

private class JvmInteger(private val integer: BigInteger) : PlatformInteger {
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
