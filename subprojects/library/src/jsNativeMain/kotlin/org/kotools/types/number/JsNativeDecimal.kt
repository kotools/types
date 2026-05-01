package org.kotools.types.number

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.kotools.types.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: Long): Decimal {
    val x: BigDecimal = BigDecimal.fromLong(value)
    return JsNativeDecimal(x)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: String): Decimal {
    val x: BigDecimal = BigDecimal.parseString(value)
    return JsNativeDecimal(x)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsNativeDecimal(private val value: BigDecimal) : Decimal {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JsNativeDecimal && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode() + "$this".hashCode()

    override fun compareTo(other: Decimal): Int {
        check(other is JsNativeDecimal)
        return this.value.compareTo(other.value)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): Decimal =
        if (this.value == BigDecimal.ZERO) this
        else JsNativeDecimal(-this.value)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toStringExpanded()
}
