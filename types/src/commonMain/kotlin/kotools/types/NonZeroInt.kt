package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NonZeroInt private constructor(private val value: Int) :
    Comparable<NonZeroInt>,
    ExplicitInt {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        internal infix fun of(value: Int): Result<NonZeroInt> = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe otherThanZero)
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: NonZeroInt): Int = compareTo(other.value)

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

// ---------- Companion ----------

private val negativeRange: IntRange = StrictlyNegativeInt.range
private val positiveRange: IntRange = StrictlyPositiveInt.range

/** The minimum value a [NonZeroInt] can have. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val NonZeroInt.Companion.min: NonZeroInt by lazy(
    negativeRange.first.toNonZeroInt()::getOrThrow
)

/** The maximum value a [NonZeroInt] can have. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val NonZeroInt.Companion.max: NonZeroInt by lazy(
    positiveRange.last.toNonZeroInt()::getOrThrow
)

/** Returns a random [NonZeroInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.Companion.random(): NonZeroInt =
    listOf(negativeRange, positiveRange)
        .random()
        .random()
        .toNonZeroInt()
        .getOrThrow()

// ---------- Unary operations ----------

/**
 * Returns this integer incremented by one, or [NonZeroInt.Companion.min] if
 * this integer equals [NonZeroInt.Companion.max], or `1` if this integer equals
 * `-1`.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.inc(): NonZeroInt =
    when (val value: Int = toInt()) {
        NonZeroInt.max.toInt() -> NonZeroInt.min
        -1 -> 1.toNonZeroInt().getOrThrow()
        else -> (value + 1).toNonZeroInt().getOrThrow()
    }

/**
 * Returns this integer decremented by one, or [NonZeroInt.Companion.max] if
 * this integer equals [NonZeroInt.Companion.min], or `-1` if this integer
 * equals `1`.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.dec(): NonZeroInt =
    when (val value: Int = toInt()) {
        NonZeroInt.min.toInt() -> NonZeroInt.max
        1 -> (-1).toNonZeroInt().getOrThrow()
        else -> (value - 1).toNonZeroInt().getOrThrow()
    }

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.unaryMinus(): NonZeroInt = (-toInt())
    .toNonZeroInt()
    .getOrThrow()

// ---------- Binary operations ----------

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(other: NonZeroInt): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(
    other: StrictlyPositiveInt
): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(
    other: StrictlyNegativeInt
): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: NonZeroInt): Int = this / other.toInt()

/**
 * Returns the remainder of truncating division of this integer by the [other]
 * one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.rem(other: NonZeroInt): Int = this % other.toInt()

// ---------- Conversions ----------

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException]
 * if this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toPositiveInt(): Result<PositiveInt> = toInt()
    .toPositiveInt()

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException]
 * if this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toNegativeInt(): Result<NegativeInt> = toInt()
    .toNegativeInt()

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    toInt().toStrictlyNegativeInt()
