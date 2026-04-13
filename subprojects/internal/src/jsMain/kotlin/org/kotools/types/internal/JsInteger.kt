package org.kotools.types.internal

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(decimal: String): PlatformInteger {
    val integer = BigInt(decimal)
    return JsInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
internal class JsInteger(private val integer: BigInt) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.integer.isEqualTo(other.integer)

    override fun hashCode(): Int = this.integer.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        check(other is JsInteger)
        val x: String = this.integer.toString()
        val y: String = other.integer.toString()
        return x.compareTo(y)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger = JsInteger(-this.integer)

    override fun plus(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        return JsInteger(this.integer + other.integer)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        return JsInteger(this.integer - other.integer)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        return JsInteger(this.integer * other.integer)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        return JsInteger(this.integer / other.integer)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        check(other is JsInteger)
        return JsInteger(this.integer % other.integer)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(value: String): BigInt

internal external object BigInt

@Suppress("UNUSED_VARIABLE", "unused")
private fun BigInt.isEqualTo(other: BigInt): Boolean {
    val x = this
    val y = other
    return js("x == y")
}

@Suppress("UNUSED_VARIABLE", "unused")
private operator fun BigInt.unaryMinus(): BigInt {
    val x = this
    val negative: dynamic = js("-x")
    return BigInt("$negative")
}

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
