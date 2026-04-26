package org.kotools.types

import java.math.BigDecimal

@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: Long): Decimal {
    val x = BigDecimal(value)
    return JvmDecimal(x)
}

@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(text: String): Decimal {
    val x = BigDecimal(text)
    return JvmDecimal(x)
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
