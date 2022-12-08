package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.aStrictlyNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly negative integers, excluding zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class StrictlyNegativeInt
private constructor(private val value: Int) : Comparable<StrictlyNegativeInt>,
    ExplicitInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < 0 }
            ?.let(::StrictlyNegativeInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyNegativeInt): Int =
        value.compareTo(other.value)

    internal fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()
        .getOrThrow()

    override fun toInt(): Int = value
}

/**
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: StrictlyNegativeInt): Int =
    this / other.toInt()
