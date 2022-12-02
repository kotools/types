package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public sealed interface ExplicitInt {
    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): ExplicitInt

    /** Returns this value as an [Int]. */
    public fun toInt(): Int

    /** Returns this integer as a [String]. */
    override fun toString(): String
}

/**
 * Compares this integer with the [other] one for order.
 * Returns zero if this integer equals the [other] one, a negative number if
 * this integer is less than the [other] one, or a positive number if
 * this integer is greater than the [other] one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.compareTo(other: ExplicitInt): Int =
    compareTo(other.toInt())

/**
 * Compares this integer with the [other] one for order.
 * Returns zero if this integer equals the [other] one, a negative number if
 * this integer is less than the [other] one, or a positive number if this
 * integer is greater than the [other] one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.compareTo(other: Int): Int = toInt()
    .compareTo(other)

/**
 * Compares this integer with the [other] one for order.
 * Returns zero if this integer equals the [other] one, a negative number if
 * this integer is less than the [other] one, or a positive number if this
 * integer is greater than the [other] one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.compareTo(other: ExplicitInt): Int =
    compareTo(other.toInt())

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.plus(other: ExplicitInt): Int = this + other.toInt()

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.plus(other: Int): Int = toInt() + other

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.plus(other: ExplicitInt): Int =
    this + other.toInt()

/** Subtracts the [other] integer from this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.minus(other: ExplicitInt): Int = this - other.toInt()

/** Subtracts the [other] integer from this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.minus(other: Int): Int = toInt() - other

/** Subtracts the [other] integer from this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.minus(other: ExplicitInt): Int =
    this - other.toInt()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.times(other: ExplicitInt): Int = this * other.toInt()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.times(other: Int): Int = toInt() * other

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.times(other: ExplicitInt): Int =
    this * other.toInt()

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.div(other: NonZeroInt): Int = toInt() / other

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.div(other: StrictlyPositiveInt): Int =
    toInt() / other

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.div(other: StrictlyNegativeInt): Int =
    toInt() / other

/**
 * Returns the remainder of truncating division of this integer by the [other]
 * one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.rem(other: NonZeroInt): Int =
    toInt() % other.toInt()

/**
 * Returns the remainder of truncating division of this integer by the [other]
 * one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.rem(other: StrictlyPositiveInt): Int =
    toInt() % other.toInt()

/**
 * Returns the remainder of truncating division of this integer by the [other]
 * one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.rem(other: StrictlyNegativeInt): Int =
    toInt() % other.toInt()

/** Returns this integer as a [NotBlankString]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun ExplicitInt.toNotBlankString(): NotBlankString = toString()
    .toNotBlankString()
    .getOrThrow()
