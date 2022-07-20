@file:Suppress("DEPRECATION")

package io.github.kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

private const val NEW_PACKAGE: String = "kotools.types.number"

/**
 * Transforms the current [Int] to a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if the current value is not greater than 0.
 */
@Deprecated(
    "Use toStrictlyPositiveInt() instead.",
    ReplaceWith("toStrictlyPositiveInt()", "$NEW_PACKAGE.toStrictlyPositiveInt")
)
@SinceKotoolsTypes("1.0")
public val Int.strictlyPositive: StrictlyPositiveInt
    get() = StrictlyPositiveInt create this

/**
 * Transforms the current [Int] to a [StrictlyPositiveInt], or returns `null` if
 * the current value is not greater than 0.
 */
@Deprecated(
    "Use toStrictlyPositiveIntOrNull() instead.",
    ReplaceWith(
        "toStrictlyPositiveIntOrNull()",
        "$NEW_PACKAGE.toStrictlyPositiveIntOrNull"
    )
)
@SinceKotoolsTypes("1.0")
public val Int.strictlyPositiveOrNull: StrictlyPositiveInt?
    get() = StrictlyPositiveInt createOrNull this

/** Type representing strictly positive integers (`> 0`). */
@Deprecated("This type will be replaced by $NEW_PACKAGE.StrictlyPositiveInt.")
@JvmInline
@SinceKotoolsTypes("1.0")
public value class StrictlyPositiveInt private constructor(
    override val value: Int
) : IntType {
    override fun toString(): String = value.toString()

    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        (value / other.value).nonZero

    public infix operator fun div(other: StrictlyNegativeInt):
            StrictlyNegativeInt = (value / other.value).strictlyNegative

    public infix operator fun div(other: StrictlyPositiveInt):
            StrictlyPositiveInt = (value / other.value).strictlyPositive

    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero

    public infix operator fun times(other: StrictlyNegativeInt):
            StrictlyNegativeInt = (value * other.value).strictlyNegative

    public infix operator fun times(other: StrictlyPositiveInt):
            StrictlyPositiveInt = (value * other.value).strictlyPositive

    public operator fun unaryMinus(): StrictlyNegativeInt =
        (-value).strictlyNegative

    internal companion object {
        private const val ERROR_MESSAGE: String =
            "Given value should be greater than 0."

        @Throws(IllegalArgumentException::class)
        infix fun create(value: Int): StrictlyPositiveInt = createOrNull(value)
            ?: throw IllegalArgumentException(ERROR_MESSAGE)

        infix fun createOrNull(value: Int): StrictlyPositiveInt? = value
            .takeIf { it > 0 }
            ?.let(::StrictlyPositiveInt)
    }
}
