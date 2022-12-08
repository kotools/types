package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of negative integers, including zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2")
public value class NegativeInt private constructor(private val value: Int) :
    Comparable<NegativeInt>,
    ExplicitInt {
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..0 }

        /** The minimum value a [NegativeInt] can have. */
        public val min: NegativeInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [NegativeInt] can have. */
        public val max: NegativeInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<NegativeInt> = value
            .takeIf { it <= 0 }
            ?.let(::NegativeInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aNegativeNumber)

        /** Returns a random [NegativeInt]. */
        public fun random(): NegativeInt = range.random()
            .toNegativeInt()
            .getOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: NegativeInt): Int = compareTo(other.value)

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2")
public operator fun NegativeInt.unaryMinus(): PositiveInt = (-toInt())
    .toPositiveInt()
    .getOrThrow()

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2")
public fun Int.toNegativeInt(): Result<NegativeInt> = NegativeInt of this
