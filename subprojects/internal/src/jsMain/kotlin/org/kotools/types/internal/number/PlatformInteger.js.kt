package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate = BigInt("$value")
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
internal actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate = BigInt(value)
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JsInteger(private val delegate: BigInt) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.delegate == other.delegate

    override fun hashCode(): Int = this.delegate.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        check(other is JsInteger)
        if (this.delegate == other.delegate) return 0
        val x: String = this.delegate.toString()
        val y: String = other.delegate.toString()
        if (!x.startsWith('-') && y.startsWith('-')) return 1
        if (x.startsWith('-') && !y.startsWith('-')) return -1
        val sign: Int = if (x.startsWith('-') && y.startsWith('-')) -1 else 1
        if (x.length > y.length) return 1 * sign
        if (x.length < y.length) return -1 * sign
        return x.compareTo(y) * sign
    }

    // ------------------------- Arithmetic operations -------------------------

    @Suppress("UNUSED_VARIABLE", "unused")
    override fun unaryMinus(): PlatformInteger {
        val x: BigInt = this.delegate
        val negative: dynamic = js("-x")
        return PlatformInteger.parse("$negative")
    }

    @Suppress("UNUSED_VARIABLE", "unused")
    override fun plus(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        val x: BigInt = this.delegate
        val y: BigInt = other.delegate
        val sum: dynamic = js("x + y")
        return PlatformInteger.parse("$sum")
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt
