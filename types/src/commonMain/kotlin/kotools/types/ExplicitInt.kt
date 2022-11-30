package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public sealed interface ExplicitInt {
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
public operator fun ExplicitInt.compareTo(other: ExplicitInt): Int =
    compareTo(other.toInt())

/**
 * Compares this integer with the [other] one for order.
 * Returns zero if this integer equals the [other] one, a negative number if
 * this integer is less than the [other] one, or a positive number if
 * this integer is greater than the [other] one.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.compareTo(other: Int): Int = toInt()
    .compareTo(other)

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
public operator fun ExplicitInt.plus(other: ExplicitInt): Int =
    this + other.toInt()

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun ExplicitInt.plus(other: Int): Int = toInt() + other

/** Adds the [other] integer to this one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.plus(other: ExplicitInt): Int = this + other.toInt()
