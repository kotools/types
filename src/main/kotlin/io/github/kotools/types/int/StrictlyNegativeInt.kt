package io.github.kotools.types.int

/**
 * Transforms the current [Int] to a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the current value is not lower than `0`.
 */
public val Int.strictlyNegative: StrictlyNegativeInt
    get() = StrictlyNegativeInt create this

/**
 * Transforms the current [Int] to a [StrictlyNegativeInt], or returns `null`
 * if the current value is not lower than `0`.
 */
public val Int.strictlyNegativeOrNull: StrictlyNegativeInt?
    get() = StrictlyNegativeInt createOrNull this

/** Type representing strictly negative integer (`< 0`). */
@JvmInline
public value class StrictlyNegativeInt private constructor(
    public val value: Int
) {
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
