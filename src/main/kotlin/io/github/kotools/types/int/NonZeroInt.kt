@file:Suppress("DEPRECATION")

package io.github.kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

private const val NEW_PACKAGE: String = "kotools.types.number"

/**
 * Transforms the current [Int] to a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the current value is `0`.
 */
@Deprecated(
    "Use toNonZeroInt() instead.",
    ReplaceWith("toNonZeroInt()", "$NEW_PACKAGE.toNonZeroInt")
)
@SinceKotoolsTypes("1.0")
public val Int.nonZero: NonZeroInt get() = NonZeroInt create this

/**
 * Transforms the current [Int] to a [NonZeroInt], or returns `null` if the
 * current value is `0`.
 */
@Deprecated(
    "Use toNonZeroIntOrNull() instead.",
    ReplaceWith("toNonZeroIntOrNull()", "$NEW_PACKAGE.toNonZeroIntOrNull")
)
@SinceKotoolsTypes("1.0")
public val Int.nonZeroOrNull: NonZeroInt? get() = NonZeroInt createOrNull this

/** Type representing integers that can't equal 0. */
@Deprecated("This type will be replaced by $NEW_PACKAGE.NonZeroInt.")
@JvmInline
@SinceKotoolsTypes("1.0")
public value class NonZeroInt private constructor(override val value: Int) :
    IntType {
    override fun toString(): String = value.toString()

    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        (value / other.value).nonZero

    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero

    public operator fun unaryMinus(): NonZeroInt = (-value).nonZero

    internal companion object {
        const val ERROR_MESSAGE: String = "Given value shouldn't equal 0."

        @Throws(IllegalArgumentException::class)
        infix fun create(value: Int): NonZeroInt = createOrNull(value)
            ?: throw IllegalArgumentException(ERROR_MESSAGE)

        infix fun createOrNull(value: Int): NonZeroInt? = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
    }
}
