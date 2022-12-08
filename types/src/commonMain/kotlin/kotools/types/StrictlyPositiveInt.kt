package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aStrictlyPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2")
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
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

/**
 * Returns this integer incremented by one, or [StrictlyPositiveInt.min] if this
 * integer equals [StrictlyPositiveInt.max].
 */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.inc(): StrictlyPositiveInt =
    if (toInt() == StrictlyPositiveInt.max.toInt()) StrictlyPositiveInt.min
    else (this + 1)
        .toStrictlyPositiveInt()
        .getOrThrow()

/**
 * Returns this integer decremented by one, or [StrictlyPositiveInt.max] if this
 * integer equals [StrictlyPositiveInt.min].
 */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.dec(): StrictlyPositiveInt =
    if (toInt() == StrictlyPositiveInt.min.toInt()) StrictlyPositiveInt.max
    else (this - 1)
        .toStrictlyPositiveInt()
        .getOrThrow()

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.unaryMinus(): StrictlyNegativeInt =
    (-toInt())
        .toStrictlyNegativeInt()
        .getOrThrow()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.times(other: NonZeroInt): NonZeroInt =
    (toInt() * other)
        .toNonZeroInt()
        .getOrThrow()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.times(
    other: StrictlyPositiveInt
): NonZeroInt = this * other.toNonZeroInt()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2")
public operator fun StrictlyPositiveInt.times(
    other: StrictlyNegativeInt
): NonZeroInt = this * other.toNonZeroInt()

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2")
public operator fun Int.div(other: StrictlyPositiveInt): Int =
    this / other.toInt()

internal fun StrictlyPositiveInt.toNonZeroInt(): NonZeroInt = toInt()
    .toNonZeroInt()
    .getOrThrow()

internal fun StrictlyPositiveInt.toPositiveInt(): PositiveInt = toInt()
    .toPositiveInt()
    .getOrThrow()

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this
