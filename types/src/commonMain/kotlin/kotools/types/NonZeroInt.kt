package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@JvmInline
@Serializable(NonZeroIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class NonZeroInt
private constructor(private val value: Int) : ExplicitInt,
    Comparable<NonZeroInt> {
    internal companion object {
        val ranges: Set<IntRange> by lazy {
            setOf(StrictlyPositiveInt.range, Int.MIN_VALUE..-1)
        }

        infix fun of(value: Int): Result<NonZeroInt> = value.takeIf { it != 0 }
            ?.toSuccessfulResult(::NonZeroInt)
            ?: Result.failure(value shouldBe otherThanZero)

        fun random(): NonZeroInt = ranges.random()
            .random()
            .toNonZeroInt()
            .getOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: NonZeroInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

internal object NonZeroIntSerializer :
    ExplicitIntSerializer<NonZeroInt>(Int::toNonZeroInt)

/**
 * Returns this integer as a [NonZeroInt], or [IllegalArgumentException] if this
 * integer equals zero.
 */
@SinceKotools(Types, "3.2")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this
