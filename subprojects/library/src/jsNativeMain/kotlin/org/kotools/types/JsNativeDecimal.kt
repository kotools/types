package org.kotools.types

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotools.types.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: Long): Decimal {
    val x: BigDecimal = BigDecimal.fromLong(value)
    return JsNativeDecimal(x)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(text: String): Decimal {
    val x: BigDecimal = BigDecimal.parseString(text)
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

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toStringExpanded()
}
