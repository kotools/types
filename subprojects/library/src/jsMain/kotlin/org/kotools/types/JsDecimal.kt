package org.kotools.types

import com.ionspin.kotlin.bignum.decimal.BigDecimal

@OptIn(ExperimentalKotoolsTypesApi::class)
@Suppress("unused")
internal actual fun Decimal(text: String): Decimal {
    val value: BigDecimal = BigDecimal.parseString(text)
    return JsDecimal(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JsDecimal(private val value: BigDecimal) : Decimal {
    override fun toString(): String = this.value.toStringExpanded()
}
