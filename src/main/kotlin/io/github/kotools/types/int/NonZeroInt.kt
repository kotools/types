package io.github.kotools.types.int

/**
 * Transforms the current [Int] to a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the current value is `0`.
 */
public val Int.nonZero: NonZeroInt get() = NonZeroInt create this

/**
 * Transforms the current [Int] to a [NonZeroInt], or returns `null` if the
 * current value is `0`.
 */
public val Int.nonZeroOrNull: NonZeroInt? get() = NonZeroInt createOrNull this

/** Type representing integers that can't equal 0. */
@JvmInline
public value class NonZeroInt private constructor(override val value: Int) :
    IntType {
    internal companion object {
        const val ERROR_MESSAGE: String = "Given value shouldn't equal 0."

        @Throws(IllegalArgumentException::class)
        infix fun create(value: Int): NonZeroInt = createOrNull(value)
            ?: throw IllegalArgumentException(ERROR_MESSAGE)

        infix fun createOrNull(value: Int): NonZeroInt? = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
    }

    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        (value / other.value).nonZero

    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero

    public operator fun unaryMinus(): NonZeroInt = (-value).nonZero
}
