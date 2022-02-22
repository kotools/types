package io.github.kotools.types.int

// TODO: Test and document
public val Int.nonZero: NonZeroInt get() = NonZeroInt create this

// TODO: Test and document
public val Int.nonZeroOrNull: NonZeroInt? get() = NonZeroInt createOrNull this

@JvmInline
public value class NonZeroInt private constructor(
    @Suppress("MemberVisibilityCanBePrivate") public val value: Int
) {
    internal companion object {
        infix fun create(value: Int): NonZeroInt = createOrNull(value)
            ?: throw IllegalArgumentException("Given value shouldn't equal 0.")

        infix fun createOrNull(value: Int): NonZeroInt? = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
    }

    // TODO: Test and document
    public infix operator fun compareTo(other: NonZeroInt): Int =
        value.compareTo(other.value)

    // TODO: Test and document
    public infix operator fun div(other: NonZeroInt): NonZeroInt =
        (value / other.value).nonZero

    // TODO: Test and document
    public infix operator fun minus(other: NonZeroInt): Int =
        value - other.value

    // TODO: Test and document
    public infix operator fun plus(other: NonZeroInt): Int =
        value + other.value

    // TODO: Test and document
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero

    // TODO: Test and document
    public operator fun unaryPlus(): NonZeroInt = (+value).nonZero

    // TODO: Test and document
    public operator fun unaryMinus(): NonZeroInt = (-value).nonZero
}
