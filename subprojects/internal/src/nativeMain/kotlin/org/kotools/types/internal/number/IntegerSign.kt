package org.kotools.types.internal.number

internal enum class IntegerSign(private val value: Int) {
    Negative(-1), Zero(0), Positive(1);

    fun compare(other: IntegerSign): Int = this.value.compareTo(other.value)

    operator fun unaryMinus(): IntegerSign = when (this) {
        Negative -> Positive
        Zero -> Zero
        Positive -> Negative
    }

    operator fun times(other: IntegerSign): IntegerSign = when {
        this == Zero || other == Zero -> Zero
        this == other -> Positive
        else -> Negative
    }
}
