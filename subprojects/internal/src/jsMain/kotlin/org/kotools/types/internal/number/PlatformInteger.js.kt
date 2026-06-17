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

    override fun times(other: PlatformInteger): PlatformInteger {
        val x: dynamic = this.delegate.asDynamic()
        val y: dynamic = (other as JsInteger).delegate.asDynamic()
        val product: BigInt = (x * y).unsafeCast<BigInt>()
        return JsInteger(product)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        val dividend: dynamic = this.delegate.asDynamic()
        val divisor: dynamic = (other as JsInteger).delegate.asDynamic()
        val remainder: dynamic =
            ((this % other) as JsInteger).delegate.asDynamic()

        val quotient: BigInt =
            ((dividend - remainder) / divisor).unsafeCast<BigInt>()

        return JsInteger(quotient)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        val dividend: dynamic = this.delegate.asDynamic()
        val divisor: dynamic = (other as JsInteger).delegate.asDynamic()

        val zero: dynamic = BigInt("0")
        val absoluteDivisor: dynamic = if (divisor < zero) -divisor else divisor

        val truncatingRemainder: dynamic = dividend % divisor
        val remainder: BigInt =
            if (truncatingRemainder < zero)
                (truncatingRemainder + absoluteDivisor).unsafeCast<BigInt>()
            else truncatingRemainder.unsafeCast<BigInt>()

        return JsInteger(remainder)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toLongOrNull(): Long? = this.toString()
        .toLongOrNull()

    override fun toString(): String = this.delegate.toString()
}

private external object BigInt

private external fun BigInt(value: String): BigInt
