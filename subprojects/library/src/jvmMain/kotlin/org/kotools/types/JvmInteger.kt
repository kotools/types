package org.kotools.types

import kotools.types.ExperimentalKotoolsTypesApi
import java.math.BigInteger

@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
@Suppress("unused")
internal actual fun Integer(text: String): Integer {
    val value = BigInteger(text)
    return JvmInteger(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JvmInteger(private val value: BigInteger) : Integer {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode()

    override fun compareTo(other: Integer): Int {
        check(other is JvmInteger)
        return this.value.compareTo(other.value)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): Integer =
        if (this == Integer.zero()) this
        else JvmInteger(-this.value)

    override fun plus(other: Integer): Integer {
        check(other is JvmInteger)
        val zero: Integer = Integer.zero()
        return when {
            this == zero -> other
            other == zero -> this
            else -> JvmInteger(this.value + other.value)
        }
    }

    override fun minus(other: Integer): Integer {
        check(other is JvmInteger)
        return if (other == Integer.zero()) this
        else JvmInteger(this.value - other.value)
    }

    override fun times(other: Integer): Integer {
        check(other is JvmInteger)
        val zero: Integer = Integer.zero()
        val one: Integer = Integer.one()
        return when {
            this == zero || other == one -> this
            other == zero || this == one -> other
            else -> JvmInteger(this.value * other.value)
        }
    }

    override fun divOrNull(other: Integer): Integer? {
        check(other is JvmInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero || other == Integer.one() -> this
            else -> JvmInteger(this.value / other.value)
        }
    }

    override fun remOrNull(other: Integer): Integer? {
        check(other is JvmInteger)
        val zero: Integer = Integer.zero()
        return when {
            other == zero -> null
            this == zero -> this
            other == Integer.one() -> zero
            else -> JvmInteger(this.value % other.value)
        }
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toString()
}
