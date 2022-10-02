package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull
import kotlin.jvm.JvmInline

// ---------- IntHolder ----------

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder {
    /** The value to hold. */
    public val value: Int
}

/** Returns the [value] as a type [T], or returns `null` if it fails. */
@SinceKotoolsTypes("1.1")
public infix fun <T : IntHolder> IntHolderCompanion<T>.orNull(value: Int): T? =
    tryOrNull { this(value) }

/** Parent of classes responsible for creating instances of [IntHolder]. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolderCompanion<out T : IntHolder> {
    /** The minimum value of the type [T]. */
    @SinceKotoolsTypes("1.1")
    public val min: T

    /** The maximum value of the type [T]. */
    @SinceKotoolsTypes("1.1")
    public val max: T

    /** Returns an instance of type [T] holding a random value. */
    public val random: T

    /**
     * Returns the [value] as a type [T], or throws an
     * [IllegalArgumentException] if it fails.
     */
    @SinceKotoolsTypes("1.1")
    @Throws(IllegalArgumentException::class)
    public operator fun invoke(value: Int): T
}

// ---------- NonZeroInt ----------

/**
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if this value equals zero.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull this

/** Representation of integers other than zero. */
@JvmInline
@Serializable
@SinceKotoolsTypes("1.1")
public value class NonZeroInt private constructor(override val value: Int) :
    IntHolder {
    public companion object : IntHolderCompanion<NonZeroInt> {
        private val negativeRange: IntRange = StrictlyNegativeInt.range
        private val positiveRange: IntRange = StrictlyPositiveInt.range
        private val ranges: Pair<IntRange, IntRange> =
            negativeRange to positiveRange
        override val min: NonZeroInt = NonZeroInt(negativeRange.first)
        override val max: NonZeroInt = NonZeroInt(positiveRange.last)
        override val random: NonZeroInt
            get() = ranges.toList()
                .random()
                .random()
                .let(this::invoke)

        override fun invoke(value: Int): NonZeroInt {
            require(value != 0) { "NonZeroInt doesn't accept 0." }
            return NonZeroInt(value)
        }
    }
}

// ---------- PositiveInt ----------

/** Representation of positive integers, including zero. */
@JvmInline
@Serializable
@SinceKotoolsTypes("1.1")
public value class PositiveInt private constructor(override val value: Int) :
    IntHolder {
    public companion object : IntHolderCompanion<PositiveInt> {
        private val range: IntRange = 0..Int.MAX_VALUE
        override val min: PositiveInt = PositiveInt(range.first)
        override val max: PositiveInt = PositiveInt(range.last)
        override val random: PositiveInt get() = this(range.random())

        override fun invoke(value: Int): PositiveInt {
            require(value >= 0) {
                "PositiveInt doesn't accept strictly negative values (tried " +
                        "with $value)."
            }
            return PositiveInt(value)
        }
    }
}

// ---------- StrictlyPositiveInt ----------

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@Serializable
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt private constructor(
    override val value: Int
) : IntHolder {
    public companion object : IntHolderCompanion<StrictlyPositiveInt> {
        internal val range: IntRange = 1..Int.MAX_VALUE
        override val min: StrictlyPositiveInt = StrictlyPositiveInt(range.first)
        override val max: StrictlyPositiveInt = StrictlyPositiveInt(range.last)
        override val random: StrictlyPositiveInt get() = this(range.random())

        override fun invoke(value: Int): StrictlyPositiveInt {
            require(value > 0) {
                "StrictlyPositiveInt doesn't accept negative values (tried " +
                        "with $value)."
            }
            return StrictlyPositiveInt(value)
        }
    }
}

// ---------- NegativeInt ----------

/** Representation of negative integers, including zero. */
@JvmInline
@Serializable
@SinceKotoolsTypes("1.1")
public value class NegativeInt private constructor(override val value: Int) :
    IntHolder {
    public companion object : IntHolderCompanion<NegativeInt> {
        private val range: IntRange = Int.MIN_VALUE..0
        override val min: NegativeInt = NegativeInt(range.first)
        override val max: NegativeInt = NegativeInt(range.last)
        override val random: NegativeInt get() = this(range.random())

        override fun invoke(value: Int): NegativeInt {
            require(value <= 0) {
                "NegativeInt doesn't accept strictly positive values (tried " +
                        "with $value)."
            }
            return NegativeInt(value)
        }
    }
}

// ---------- StrictlyNegativeInt ----------

/** Representation of strictly negative integers, excluding zero. */
@JvmInline
@Serializable
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt private constructor(
    override val value: Int
) : IntHolder {
    public companion object : IntHolderCompanion<StrictlyNegativeInt> {
        internal val range: IntRange = Int.MIN_VALUE..-1
        override val min: StrictlyNegativeInt = StrictlyNegativeInt(range.first)
        override val max: StrictlyNegativeInt = StrictlyNegativeInt(range.last)
        override val random: StrictlyNegativeInt get() = this(range.random())

        override fun invoke(value: Int): StrictlyNegativeInt {
            require(value < 0) {
                "StrictlyNegativeInt doesn't accept positive values (tried " +
                        "with $value)."
            }
            return StrictlyNegativeInt(value)
        }
    }
}
