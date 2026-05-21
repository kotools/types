package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val decimal: String = value.toString()
    return PlatformInteger(decimal)
}

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate = BigInt(value)
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private value class JsInteger(private val delegate: BigInt) : PlatformInteger {
    override fun compareTo(other: PlatformInteger): Int {
        val x = this.delegate.asDynamic()
        val y = (other as JsInteger).delegate.asDynamic()
        return if (x < y) -1 else if (x > y) 1 else 0
    }

    override fun unaryMinus(): PlatformInteger {
        val negated: BigInt = (-this.delegate.asDynamic()).unsafeCast<BigInt>()
        return JsInteger(negated)
    }

    override fun plus(other: PlatformInteger): PlatformInteger {
        val x: dynamic = this.delegate.asDynamic()
        val y: dynamic = (other as JsInteger).delegate.asDynamic()
        val sum: BigInt = (x + y).unsafeCast<BigInt>()
        return JsInteger(sum)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        val x: dynamic = this.delegate.asDynamic()
        val y: dynamic = (other as JsInteger).delegate.asDynamic()
        val difference: BigInt = (x - y).unsafeCast<BigInt>()
        return JsInteger(difference)
    }

    override fun toString(): String = this.delegate.toString()
}

private external object BigInt

private external fun BigInt(value: String): BigInt
