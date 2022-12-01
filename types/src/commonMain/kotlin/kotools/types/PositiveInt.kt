package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.aPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of positive integers, including zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class PositiveInt
private constructor(private val value: Int) : Comparable<PositiveInt>,
    ExplicitInt {
    internal companion object {
        infix fun of(value: Int): Result<PositiveInt> = value.takeIf { it >= 0 }
            ?.let(::PositiveInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aPositiveNumber)
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: PositiveInt): Int = compareTo(other.value)

    override fun toInt(): Int = value
}

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this
