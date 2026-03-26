package org.kotools.types.internal

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(decimal: String): PlatformInteger {
    val integer = BigInt(decimal)
    return JsInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
internal class JsInteger(private val integer: BigInt) : PlatformInteger {
    // ------------------------- Arithmetic operations -------------------------

    override fun plus(other: PlatformInteger): PlatformInteger {
        if (other !is JsInteger) Error.prohibitedAddition(this, other)
        return JsInteger(this.integer + other.integer)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        if (other !is JsInteger) Error.prohibitedSubtraction(this, other)
        return JsInteger(this.integer - other.integer)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        if (other !is JsInteger) Error.prohibitedMultiplication(this, other)
        return JsInteger(this.integer * other.integer)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        if (other !is JsInteger) Error.prohibitedDivision(this, other)
        return JsInteger(this.integer / other.integer)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        if (other !is JsInteger) Error.prohibitedRemainder(this, other)
        return JsInteger(this.integer % other.integer)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(value: String): BigInt

internal external object BigInt

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.plus(other: BigInt): BigInt {
    val x = this
    val y = other
    val sum: dynamic = js("x + y")
    return BigInt("$sum")
}

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.minus(other: BigInt): BigInt {
    val x = this
    val y = other
    val difference: dynamic = js("x - y")
    return BigInt("$difference")
}

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.times(other: BigInt): BigInt {
    val x = this
    val y = other
    val product: dynamic = js("x * y")
    return BigInt("$product")
}

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.div(other: BigInt): BigInt {
    val x = this
    val y = other
    val quotient: dynamic = js("x / y")
    return BigInt("$quotient")
}

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.rem(other: BigInt): BigInt {
    val x = this
    val y = other
    val remainder: dynamic = js("x % y")
    return BigInt("$remainder")
}
