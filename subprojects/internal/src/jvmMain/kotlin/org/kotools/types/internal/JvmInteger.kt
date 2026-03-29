package org.kotools.types.internal

import java.math.BigInteger

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(decimal: String): PlatformInteger {
    val value = BigInteger(decimal)
    return JvmInteger(value)
}

@OptIn(InternalKotoolsTypesApi::class)
internal class JvmInteger(private val integer: BigInteger) : PlatformInteger {
    // ------------------------- Arithmetic operations -------------------------

    override fun plus(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        return JvmInteger(this.integer + other.integer)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        return JvmInteger(this.integer - other.integer)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        return JvmInteger(this.integer * other.integer)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        return JvmInteger(this.integer / other.integer)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        return JvmInteger(this.integer % other.integer)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.integer.toString()
}
