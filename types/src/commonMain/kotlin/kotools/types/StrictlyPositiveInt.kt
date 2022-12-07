package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.aStrictlyPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class StrictlyPositiveInt
private constructor(private val value: Int) : Comparable<StrictlyPositiveInt>,
    ExplicitInt {
    public companion object {
        internal val range: IntRange by lazy { 1..Int.MAX_VALUE }

        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > 0 }
            ?.let(::StrictlyPositiveInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        /** Returns a random [StrictlyPositiveInt]. */
        public fun random(): StrictlyPositiveInt = range.random()
            .toStrictlyPositiveInt()
            .getOrThrow()

    }

    /**
     * Returns this integer incremented by one, or [StrictlyPositiveInt.min] if
     * this integer equals [StrictlyPositiveInt.max].
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else of(value + 1).getOrThrow()

    /**
     * Returns this integer decremented by one, or [StrictlyPositiveInt.max] if
     * this integer equals [StrictlyPositiveInt.min].
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else of(value - 1).getOrThrow()

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): StrictlyNegativeInt = StrictlyNegativeInt
        .of(-value)
        .getOrThrow()

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveInt): Int =
        value.compareTo(other.value)

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        this * other.toNonZeroInt()

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        this * other.toNonZeroInt()

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: NonZeroInt): NonZeroInt = NonZeroInt
        .of(value * other)
        .getOrThrow()

    internal fun toPositiveInt(): PositiveInt = value.toPositiveInt()
        .getOrThrow()

    private fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()
        .getOrThrow()

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: StrictlyPositiveInt): Int =
    this / other.toInt()

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this
