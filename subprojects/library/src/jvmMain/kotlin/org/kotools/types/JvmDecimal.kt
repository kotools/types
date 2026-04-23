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
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmDecimal && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode()

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toString()
}
