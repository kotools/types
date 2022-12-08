package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NonZeroInt private constructor(private val value: Int) :
    Comparable<NonZeroInt>,
    ExplicitInt {
    internal companion object {
        infix fun of(value: Int): Result<NonZeroInt> = value.takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(
                IllegalArgumentException("Given integer shouldn't equal 0.")
            )
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: NonZeroInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value
}

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this
