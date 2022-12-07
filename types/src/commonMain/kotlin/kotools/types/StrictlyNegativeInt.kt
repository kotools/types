package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aStrictlyNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly negative integers, excluding zero. */
@JvmInline
@SinceKotools(Types, "3.2")
public value class StrictlyNegativeInt
private constructor(private val value: Int) : Comparable<StrictlyNegativeInt>,
    ExplicitInt {
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < 0 }
            ?.let(::StrictlyNegativeInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)

        /** Returns a random [StrictlyNegativeInt]. */
        public fun random(): StrictlyNegativeInt = range.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): StrictlyPositiveInt = StrictlyPositiveInt
        .of(-value)
        .getOrThrow()

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyNegativeInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value

    internal fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()
        .getOrThrow()

    override fun toString(): String = "$value"
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2")
public operator fun Int.div(other: StrictlyNegativeInt): Int =
    this / other.toInt()

/**
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is positive.
 */
@SinceKotools(Types, "3.2")
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this
