package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.aPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of positive integers, including zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class PositiveInt
private constructor(private val value: Int) : Comparable<PositiveInt>,
    ExplicitInt {
    public companion object {
        private val range: IntRange by lazy { 0..Int.MAX_VALUE }

        /** The minimum value a [PositiveInt] can have. */
        public val min: PositiveInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [PositiveInt] can have. */
        public val max: PositiveInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<PositiveInt> = value
            .takeIf { it >= 0 }
            ?.let(::PositiveInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aPositiveNumber)

        /** Returns a random [PositiveInt]. */
        public fun random(): PositiveInt = range.random()
            .toPositiveInt()
            .getOrThrow()
    }

    /**
     * Returns this integer incremented by one, or [PositiveInt.min] if this
     * integer equals [PositiveInt.max].
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else of(value + 1).getOrThrow()

    /**
     * Returns this integer decremented by one, or [PositiveInt.max] if this
     * integer equals [PositiveInt.min].
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else of(value - 1).getOrThrow()

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: PositiveInt): Int = compareTo(other.value)

    override fun toInt(): Int = value

    /**
     * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException]
     * if this integer equals zero.
     */
    public fun toNonZeroInt(): Result<NonZeroInt> = value.toNonZeroInt()

    /**
     * Returns this integer as a [NegativeInt], or an [IllegalArgumentException]
     * if this integer is strictly positive.
     */
    public fun toNegativeInt(): Result<NegativeInt> = value.toNegativeInt()

    /**
     * Returns this integer as a [StrictlyPositiveInt], or an
     * [IllegalArgumentException] if this integer equals zero.
     */
    public fun toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
        value.toStrictlyPositiveInt()

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this
