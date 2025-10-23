package org.kotools.types.numbers

internal actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer = BigInt("$number")
    return JsInteger(integer)
}

internal actual fun PlatformInteger(text: String): PlatformInteger {
    PlatformInteger.requirements(text)
    val integer = BigInt(text)
    return JsInteger(integer)
}

private class JsInteger(private val integer: BigInt) : PlatformInteger {
    override fun plus(other: PlatformInteger): PlatformInteger {
        val sum: BigInt = this.integer + BigInt("$other")
        return JsInteger(sum)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        val difference: BigInt = this.integer - BigInt("$other")
        return JsInteger(difference)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        val product: BigInt = this.integer * BigInt("$other")
        return JsInteger(product)
    }

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.plus(other: BigInt): BigInt {
    val x = this
    val y = other
    val sum: dynamic = js("x + y")
    return BigInt("$sum")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.minus(other: BigInt): BigInt {
    val x = this
    val y = other
    val difference: dynamic = js("x - y")
    return BigInt("$difference")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.times(other: BigInt): BigInt {
    val x = this
    val y = other
    val product: dynamic = js("x * y")
    return BigInt("$product")
}
