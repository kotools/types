package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of positive integers, including zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2")
public value class PositiveInt
private constructor(private val value: Int) : Comparable<PositiveInt>,
    ExplicitInt {
    public companion object {
        private val range: IntRange by lazy { 0..Int.MAX_VALUE }

        /** The minimum value a [PositiveInt] can have. */
        public val min: PositiveInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [PositiveInt] can have. */
        public val max: PositiveInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<PositiveInt> = value
            .takeIf { it >= 0 }
            ?.let(::PositiveInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aPositiveNumber)

        /** Returns a random [PositiveInt]. */
        public fun random(): PositiveInt = range.random()
            .toPositiveInt()
            .getOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: PositiveInt): Int = compareTo(other.value)

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

/**
 * Returns this integer incremented by one, or [PositiveInt.min] if this integer
 * equals [PositiveInt.max].
 */
@SinceKotools(Types, "3.2")
public operator fun PositiveInt.inc(): PositiveInt =
    if (toInt() == PositiveInt.max.toInt()) PositiveInt.min
    else (this + 1).toPositiveInt().getOrThrow()

/**
 * Returns this integer decremented by one, or [PositiveInt.max] if this integer
 * equals [PositiveInt.min].
 */
@SinceKotools(Types, "3.2")
public operator fun PositiveInt.dec(): PositiveInt =
    if (toInt() == PositiveInt.min.toInt()) PositiveInt.max
    else (this - 1).toPositiveInt().getOrThrow()

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2")
public operator fun PositiveInt.unaryMinus(): NegativeInt = (-toInt())
    .toNegativeInt()
    .getOrThrow()

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this
