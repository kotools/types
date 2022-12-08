package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NonZeroInt private constructor(private val value: Int) :
    Comparable<NonZeroInt>,
    ExplicitInt {
    public companion object {
        private val negativeRange: IntRange = StrictlyNegativeInt.range
        private val positiveRange: IntRange = StrictlyPositiveInt.range

        /** The minimum value a [NonZeroInt] can have. */
        public val min: NonZeroInt by lazy(of(negativeRange.first)::getOrThrow)

        /** The maximum value a [NonZeroInt] can have. */
        public val max: NonZeroInt by lazy(of(positiveRange.last)::getOrThrow)

        internal infix fun of(value: Int): Result<NonZeroInt> = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe otherThanZero)

        /** Returns a random [NonZeroInt]. */
        public fun random(): NonZeroInt = listOf(negativeRange, positiveRange)
            .random()
            .random()
            .toNonZeroInt()
            .getOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: NonZeroInt): Int = compareTo(other.value)

    override fun toInt(): Int = value

    /**
     * Returns this integer as a [PositiveInt], or an [IllegalArgumentException]
     * if this integer is strictly negative.
     */
    public fun toPositiveInt(): Result<PositiveInt> = value.toPositiveInt()

    /**
     * Returns this integer as a [NegativeInt], or an [IllegalArgumentException]
     * if this integer is strictly positive.
     */
    public fun toNegativeInt(): Result<NegativeInt> = value.toNegativeInt()

    /**
     * Returns this integer as a [StrictlyPositiveInt], or an
     * [IllegalArgumentException] if this integer is strictly negative.
     */
    public fun toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
        value.toStrictlyPositiveInt()

    /**
     * Returns this integer as a [StrictlyNegativeInt], or an
     * [IllegalArgumentException] if this integer is strictly positive.
     */
    public fun toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
        value.toStrictlyNegativeInt()

    /** Returns this integer as a [String]. */
    override fun toString(): String = "$value"

    /** Returns this integer as a [NotBlankString]. */
    public fun toNotBlankString(): NotBlankString = toString()
        .toNotBlankString()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: NonZeroInt): Int = this / other.toInt()

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this
