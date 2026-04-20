package org.kotools.types

import com.ionspin.kotlin.bignum.integer.BigInteger

@OptIn(ExperimentalKotoolsTypesApi::class)
@Suppress("unused")
internal actual fun Integer(text: String): Integer {
    val value: BigInteger = BigInteger.parseString(text)
    return NativeInteger(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class NativeInteger(private val value: BigInteger) : Integer {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is NativeInteger && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode()

    override fun compareTo(other: Integer): Int {
        check(other is NativeInteger)
        return this.value.compare(other.value)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): Integer =
        if (this == Integer.zero()) this
        else NativeInteger(-this.value)

    override fun plus(other: Integer): Integer {
        check(other is NativeInteger)
        val zero: Integer = Integer.zero()
        return when {
            this == zero -> other
            other == zero -> this
            else -> NativeInteger(this.value + other.value)
        }
    }

    override fun minus(other: Integer): Integer {
        check(other is NativeInteger)
        return if (other == Integer.zero()) this
        else NativeInteger(this.value - other.value)
    }

    override fun times(other: Integer): Integer {
        check(other is NativeInteger)
        val zero: Integer = Integer.zero()
        val one: Integer = Integer.one()
        return when {
            this == zero || other == one -> this
            other == zero || this == one -> other
            else -> NativeInteger(this.value * other.value)
        }
    }

    override fun divOrNull(other: Integer): Integer? {
        check(other is NativeInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero || other == Integer.one() -> this
            else -> NativeInteger(this.value / other.value)
        }
    }

    override fun remOrNull(other: Integer): Integer? {
        check(other is NativeInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero -> this
            other == Integer.one() -> zero
            else -> NativeInteger(this.value % other.value)
        }
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toString()
}
