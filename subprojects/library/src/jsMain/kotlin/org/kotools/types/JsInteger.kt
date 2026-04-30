package org.kotools.types

import kotools.types.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
@Suppress("unused")
internal actual fun Integer(text: String): Integer {
    val value = BigInt(text)
    return JsInteger(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsInteger(private val value: BigInt) : Integer {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.value.isEqualTo(other.value)

    override fun hashCode(): Int = this.value.hashCode()

    override fun compareTo(other: Integer): Int {
        check(other is JsInteger)
        val x: String = this.value.toString()
        val y: String = other.value.toString()
        return x.compareTo(y)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): Integer =
        if (this == Integer.zero()) this
        else JsInteger(-this.value)

    override fun plus(other: Integer): Integer {
        check(other is JsInteger)
        val zero: Integer = Integer.zero()
        return when {
            this == zero -> other
            other == zero -> this
            else -> JsInteger(this.value + other.value)
        }
    }

    override fun minus(other: Integer): Integer {
        check(other is JsInteger)
        return if (other == Integer.zero()) this
        else JsInteger(this.value - other.value)
    }

    override fun times(other: Integer): Integer {
        check(other is JsInteger)
        val zero: Integer = Integer.zero()
        val one: Integer = Integer.one()
        return when {
            this == zero || other == one -> this
            other == zero || this == one -> other
            else -> JsInteger(this.value * other.value)
        }
    }

    override fun divOrNull(other: Integer): Integer? {
        check(other is JsInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero || other == Integer.one() -> this
            else -> JsInteger(this.value / other.value)
        }
    }

    override fun remOrNull(other: Integer): Integer? {
        check(other is JsInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero -> this
            other == Integer.one() -> zero
            else -> JsInteger(this.value % other.value)
        }
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt

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
