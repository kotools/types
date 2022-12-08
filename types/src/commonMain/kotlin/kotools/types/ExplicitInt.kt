package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public sealed interface ExplicitInt {
    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    public operator fun compareTo(other: Int): Int = toInt().compareTo(other)

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    public operator fun compareTo(other: ExplicitInt): Int =
        compareTo(other.toInt())

    /** Adds the [other] integer to this one. */
    public operator fun plus(other: Int): Int = toInt() + other

    /** Adds the [other] integer to this one. */
    public operator fun plus(other: ExplicitInt): Int = this + other.toInt()

    /** Subtracts the [other] integer from this one. */
    public operator fun minus(other: Int): Int = toInt() - other

    /** Subtracts the [other] integer from this one. */
    public operator fun minus(other: ExplicitInt): Int = this - other.toInt()

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: Int): Int = toInt() * other

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: ExplicitInt): Int = this * other.toInt()

    /**
     * Divides this integer by the [other] one, truncating the result to an integer
     * that is closer to zero.
     */
    public operator fun div(other: NonZeroInt): Int = toInt() / other

    /**
     * Divides this integer by the [other] one, truncating the result to an integer
     * that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): Int = toInt() / other

    /**
     * Divides this integer by the [other] one, truncating the result to an integer
     * that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): Int = toInt() / other

    /**
     * Returns the remainder of truncating division of this integer by the
     * [other] one.
     */
    public operator fun rem(other: NonZeroInt): Int = toInt() % other.toInt()

    /**
     * Returns the remainder of truncating division of this integer by the
     * [other] one.
     */
    public operator fun rem(other: StrictlyPositiveInt): Int =
        toInt() % other.toInt()

    /**
     * Returns the remainder of truncating division of this integer by the
     * [other] one.
     */
    public operator fun rem(other: StrictlyNegativeInt): Int =
        toInt() % other.toInt()

    /** Returns this value as an [Int]. */
    public fun toInt(): Int
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

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.plus(other: ExplicitInt): Int = this + other.toInt()

/** Subtracts the [other] integer from this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.minus(other: ExplicitInt): Int = this - other.toInt()

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.times(other: ExplicitInt): Int = this * other.toInt()
