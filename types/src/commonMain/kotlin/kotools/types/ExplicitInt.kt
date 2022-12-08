package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.number.*
import kotlin.jvm.JvmInline

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public sealed interface ExplicitInt {
    /** Returns this value as an [Int]. */
    public fun toInt(): Int

    /** Returns this integer as a [String]. */
    override fun toString(): String
}

/** Representation of integers other than zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NonZeroInt private constructor(private val value: Int) :
    Comparable<NonZeroInt>,
    ExplicitInt {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        private val negativeRange: IntRange by lazy(
            StrictlyNegativeInt.Companion::range
        )
        private val positiveRange: IntRange by lazy(
            StrictlyPositiveInt.Companion::range
        )

        /** The minimum value a [NonZeroInt] can have. */
        public val min: NonZeroInt by lazy(
            negativeRange.first.toNonZeroInt()::getOrThrow
        )

        /** The maximum value a [NonZeroInt] can have. */
        public val max: NonZeroInt by lazy(
            positiveRange.last.toNonZeroInt()::getOrThrow
        )

        internal infix fun of(value: Int): Result<NonZeroInt> = value
            .takeIf { it != 0 }
            ?.let(::NonZeroInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe otherThanZero)

        /** Returns a random [NonZeroInt]. */
        public fun random(): NonZeroInt =
            listOf(negativeRange, positiveRange)
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

    override fun toString(): String = "$value"
}

/** Representation of positive integers, including zero. */
@JvmInline
@Serializable
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
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

/** Representation of negative integers, including zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class NegativeInt private constructor(private val value: Int) :
    Comparable<NegativeInt>,
    ExplicitInt {
    public companion object {
        private val range: IntRange by lazy { Int.MIN_VALUE..0 }

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

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class StrictlyPositiveInt
private constructor(private val value: Int) : Comparable<StrictlyPositiveInt>,
    ExplicitInt {
    public companion object {
        internal val range: IntRange by lazy { 1..Int.MAX_VALUE }

        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > 0 }
            ?.let(::StrictlyPositiveInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        /** Returns a random [StrictlyPositiveInt]. */
        public fun random(): StrictlyPositiveInt = range.random()
            .toStrictlyPositiveInt()
            .getOrThrow()

    }

    /**
     * Returns this integer incremented by one, or [StrictlyPositiveInt.min] if
     * this integer equals [StrictlyPositiveInt.max].
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else of(value + 1).getOrThrow()

    /**
     * Returns this integer decremented by one, or [StrictlyPositiveInt.max] if
     * this integer equals [StrictlyPositiveInt.min].
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else of(value - 1).getOrThrow()

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): StrictlyNegativeInt = StrictlyNegativeInt
        .of(-value)
        .getOrThrow()

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveInt): Int =
        value.compareTo(other.value)

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        this * other.toNonZeroInt()

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        this * other.toNonZeroInt()

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: NonZeroInt): NonZeroInt = NonZeroInt
        .of(value * other)
        .getOrThrow()

    internal fun toPositiveInt(): PositiveInt = value.toPositiveInt()
        .getOrThrow()

    private fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()
        .getOrThrow()

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

/** Representation of strictly negative integers, excluding zero. */
@JvmInline
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public value class StrictlyNegativeInt
private constructor(private val value: Int) : Comparable<StrictlyNegativeInt>,
    ExplicitInt {
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(of(range.first)::getOrThrow)

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(of(range.last)::getOrThrow)

        internal infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < 0 }
            ?.let(::StrictlyNegativeInt)
            ?.let(Result.Companion::success)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)

        /** Returns a random [StrictlyNegativeInt]. */
        public fun random(): StrictlyNegativeInt = range.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): StrictlyPositiveInt = StrictlyPositiveInt
        .of(-value)
        .getOrThrow()

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * this integer is less than the [other] one, or a positive number if
     * this integer is greater than the [other] one.
     */
    override fun compareTo(other: StrictlyNegativeInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value

    internal fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()
        .getOrThrow()

    override fun toString(): String = "$value"
}

/**
 * Returns this integer incremented by one, or [NonZeroInt.Companion.min] if
 * this integer equals [NonZeroInt.Companion.max], or `1` if this integer equals
 * `-1`.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.inc(): NonZeroInt =
    when (val value: Int = toInt()) {
        NonZeroInt.max.toInt() -> NonZeroInt.min
        -1 -> 1.toNonZeroInt().getOrThrow()
        else -> (value + 1).toNonZeroInt().getOrThrow()
    }

/**
 * Returns this integer incremented by one, or [PositiveInt.min] if this integer
 * equals [PositiveInt.max].
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun PositiveInt.inc(): PositiveInt =
    if (toInt() == PositiveInt.max.toInt()) PositiveInt.min
    else (this + 1).toPositiveInt().getOrThrow()

/**
 * Returns this integer decremented by one, or [NonZeroInt.Companion.max] if
 * this integer equals [NonZeroInt.Companion.min], or `-1` if this integer
 * equals `1`.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.dec(): NonZeroInt =
    when (val value: Int = toInt()) {
        NonZeroInt.min.toInt() -> NonZeroInt.max
        1 -> (-1).toNonZeroInt().getOrThrow()
        else -> (value - 1).toNonZeroInt().getOrThrow()
    }

/**
 * Returns this integer decremented by one, or [PositiveInt.max] if this integer
 * equals [PositiveInt.min].
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun PositiveInt.dec(): PositiveInt =
    if (toInt() == PositiveInt.min.toInt()) PositiveInt.max
    else (this - 1).toPositiveInt().getOrThrow()

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.unaryMinus(): NonZeroInt = (-toInt())
    .toNonZeroInt()
    .getOrThrow()

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun PositiveInt.unaryMinus(): NegativeInt = (-toInt())
    .toNegativeInt()
    .getOrThrow()

/** Returns the negative of this integer. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NegativeInt.unaryMinus(): PositiveInt = (-toInt())
    .toPositiveInt()
    .getOrThrow()

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

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(other: NonZeroInt): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(other: StrictlyPositiveInt): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/** Multiplies this integer by the [other] one. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun NonZeroInt.times(other: StrictlyNegativeInt): NonZeroInt {
    val result: Int = toInt() * other
    return result.toNonZeroInt().getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: NonZeroInt): Int = this / other.toInt()

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: StrictlyPositiveInt): Int =
    this / other.toInt()

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public operator fun Int.div(other: StrictlyNegativeInt): Int =
    this / other.toInt()

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
public operator fun Int.rem(other: NonZeroInt): Int = this % other.toInt()

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

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun PositiveInt.toNonZeroInt(): Result<NonZeroInt> = toInt()
    .toNonZeroInt()

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException]
 * if this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toPositiveInt(): Result<PositiveInt> = toInt()
    .toPositiveInt()

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toNegativeInt(): Result<NegativeInt> = NegativeInt of this

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException]
 * if this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toNegativeInt(): Result<NegativeInt> = toInt()
    .toNegativeInt()

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun PositiveInt.toNegativeInt(): Result<NegativeInt> = toInt()
    .toNegativeInt()

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun PositiveInt.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this

/**
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun NonZeroInt.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    toInt().toStrictlyNegativeInt()

/** Returns this integer as a [NotBlankString]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun ExplicitInt.toNotBlankString(): NotBlankString = toString()
    .toNotBlankString()
    .getOrThrow()
