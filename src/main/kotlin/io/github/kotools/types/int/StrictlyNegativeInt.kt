package io.github.kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Transforms the current [Int] to a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the current value is not lower than `0`.
 */
@SinceKotoolsTypes("1.0")
public val Int.strictlyNegative: StrictlyNegativeInt
    get() = StrictlyNegativeInt create this

/**
 * Transforms the current [Int] to a [StrictlyNegativeInt], or returns `null`
 * if the current value is not lower than `0`.
 */
@SinceKotoolsTypes("1.0")
public val Int.strictlyNegativeOrNull: StrictlyNegativeInt?
    get() = StrictlyNegativeInt createOrNull this

/** Type representing strictly negative integers (`< 0`). */
@JvmInline
@SinceKotoolsTypes("1.0")
public value class StrictlyNegativeInt private constructor(
    override val value: Int
) : IntType {
    override fun toString(): String = value.toString()

    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        (value / other.value).nonZero

    public infix operator fun div(other: StrictlyNegativeInt):
            StrictlyPositiveInt = (value / other.value).strictlyPositive

    public infix operator fun div(other: StrictlyPositiveInt):
            StrictlyNegativeInt = (value / other.value).strictlyNegative

    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero

    public infix operator fun times(other: StrictlyNegativeInt):
            StrictlyPositiveInt = (value * other.value).strictlyPositive

    public infix operator fun times(other: StrictlyPositiveInt):
            StrictlyNegativeInt = (value * other.value).strictlyNegative

    public operator fun unaryMinus(): StrictlyPositiveInt =
        (-value).strictlyPositive

    internal companion object {
        private const val ERROR_MESSAGE: String =
            "Given value should be lower than 0."

        @Throws(IllegalArgumentException::class)
        infix fun create(value: Int): StrictlyNegativeInt = createOrNull(value)
            ?: throw IllegalArgumentException(ERROR_MESSAGE)

        infix fun createOrNull(value: Int): StrictlyNegativeInt? = value
            .takeIf { it < 0 }
            ?.let(::StrictlyNegativeInt)
    }
}
