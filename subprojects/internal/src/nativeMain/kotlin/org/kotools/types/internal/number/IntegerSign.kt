package org.kotools.types.internal.number

internal value class IntegerSign private constructor(private val value: Int) {
    init {
        require(this.value in -1..1) {
            "Sign must be -1 for negative values, 0 for zero, or 1 for " +
                    "positive values (was: ${this.value})."
        }
    }

    companion object {
        val Zero: IntegerSign = IntegerSign(0)
        val Positive: IntegerSign = IntegerSign(1)
        val Negative: IntegerSign = IntegerSign(-1)
    }

    operator fun compareTo(other: IntegerSign): Int =
        this.value.compareTo(other.value)

    operator fun unaryMinus(): IntegerSign = IntegerSign(-this.value)

    operator fun times(other: IntegerSign): IntegerSign =
        IntegerSign(this.value * other.value)
}
