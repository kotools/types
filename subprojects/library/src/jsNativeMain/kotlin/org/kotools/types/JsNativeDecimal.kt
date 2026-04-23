package org.kotools.types

import com.ionspin.kotlin.bignum.decimal.BigDecimal

@OptIn(ExperimentalKotoolsTypesApi::class)
@Suppress("unused")
internal actual fun Decimal(text: String): Decimal {
    val value: BigDecimal = BigDecimal.parseString(text)
    return JsNativeDecimal(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsNativeDecimal(private val value: BigDecimal) : Decimal {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JsNativeDecimal && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode()

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toStringExpanded()
}
