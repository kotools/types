package org.kotools.types

import java.math.BigDecimal

@OptIn(ExperimentalKotoolsTypesApi::class)
@JvmSynthetic
@Suppress("unused")
internal actual fun Decimal(text: String): Decimal {
    val value = BigDecimal(text)
    return JvmDecimal(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JvmDecimal(private val value: BigDecimal) : Decimal {
    override fun toString(): String = this.value.toString()
}
