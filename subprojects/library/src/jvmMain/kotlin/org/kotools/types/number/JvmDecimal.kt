package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import java.math.BigDecimal

@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: Long): Decimal {
    val x = BigDecimal(value)
    return JvmDecimal(x)
}

@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
internal actual fun Decimal(value: String): Decimal {
    val x = BigDecimal(value)
    return JvmDecimal(x)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
private class JvmDecimal(private val value: BigDecimal) : Decimal {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmDecimal && this.value == other.value

    override fun hashCode(): Int = this.value.hashCode()

    override fun compareTo(other: Decimal): Int {
        check(other is JvmDecimal)
        return this.value.compareTo(other.value)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): Decimal =
        if (this.value == BigDecimal.ZERO) this
        else JvmDecimal(-this.value)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.value.toString()
}
