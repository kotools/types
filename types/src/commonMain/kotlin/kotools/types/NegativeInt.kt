package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.aNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of negative integers, including zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NegativeInt private constructor(private val value: Int) :
    Comparable<NegativeInt>,
    ExplicitInt {
    internal companion object {
        infix fun of(value: Int): Result<NegativeInt> = value.takeIf { it <= 0 }
            ?.let(::NegativeInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aNegativeNumber)
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: NegativeInt): Int = compareTo(other.value)

    override fun toInt(): Int = value
}

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNegativeInt(): Result<NegativeInt> = NegativeInt of this
