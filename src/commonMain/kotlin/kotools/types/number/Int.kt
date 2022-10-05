package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.Holder
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString
import kotlin.jvm.JvmInline

// ---------- IntHolder ----------

/**
 * Compares this value with the [other] value for order.
 * Returns zero if this value equals the [other] value, a negative number if
 * this value is less than the [other] value, or a positive number if this value
 * is greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.minus(other: IntHolder): Int = minus(other.value)

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("3.0")
public operator fun Int.times(other: IntHolder): Int = times(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Holder<Int> {
    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public operator fun compareTo(other: Int): Int = value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public operator fun compareTo(other: IntHolder): Int =
        compareTo(other.value)

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public operator fun plus(other: IntHolder): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public operator fun minus(other: IntHolder): Int = minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: IntHolder): Int = times(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: NonZeroIntHolder): Int = value / other.value

    // ---------- Conversions ----------

    /**
     * Returns the string representation of the [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        toString().toNotBlankString()
}

internal sealed class IntHolderSerializer<T : IntHolder>(
    private val builder: (Int) -> T
) : KSerializer<T> {
    private val delegate: KSerializer<Int> = Int.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: T): Unit =
        delegate.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder): T {
        val value: Int = delegate.deserialize(decoder)
        return builder(value)
    }
}

// ---------- IntHolderCompanion ----------

/** Parent of companion objects in subtypes of [IntHolder]. */
@SinceKotoolsTypes("3.0")
public sealed class IntHolderCompanion<out T : IntHolder>(
    protected val builder: (Int) -> T
) {
    /** The minimum value of the type [T]. */
    public abstract val min: T

    /** The maximum value of the type [T]. */
    public abstract val max: T

    /** Returns an instance of type [T] holding a random value. */
    public abstract val random: T

    /** Returns the [value] as a type [T], or returns `null` if it fails. */
    public infix fun orNull(value: Int): T? = tryOrNull { builder(value) }
}

// ---------- NonZeroIntHolder ----------

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotoolsTypes("3.0")
public operator fun Int.div(other: NonZeroIntHolder): Int = div(other.value)

/** Parent of classes responsible for holding non-zero integers. */
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroIntHolder : IntHolder {
    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroIntHolder): NonZeroInt =
        times(other.value).toNonZeroInt()
}

// ---------- PositiveIntHolder ----------

/** Parent of classes responsible for holding positive integers. */
@SinceKotoolsTypes("3.0")
public sealed interface PositiveIntHolder : IntHolder {
    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        super.div(other).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        super.div(other).toNegativeInt()
}

// ---------- NegativeIntHolder ----------

/** Parent of classes responsible for holding negative integers. */
@SinceKotoolsTypes("3.0")
public sealed interface NegativeIntHolder : IntHolder {
    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        super.div(other).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        super.div(other).toPositiveInt()
}

// ---------- NonZeroInt ----------

/**
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if this value equals zero.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNonZeroInt(): NonZeroInt = toInt().toNonZeroInt()

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull this

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents zero.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNonZeroIntOrNull(): NonZeroInt? =
    toIntOrNull()?.toNonZeroIntOrNull()

/**
 * Representation of integers other than zero.
 *
 * @constructor Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the [value] equals zero.
 */
@JvmInline
@Serializable(NonZeroIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class NonZeroInt
@Throws(IllegalArgumentException::class)
constructor(override val value: Int) : Comparable<NonZeroInt>,
    NonZeroIntHolder {
    init {
        require(value != 0) { "NonZeroInt doesn't accept 0." }
    }

    /** Contains static declarations for creating or holding a [NonZeroInt]. */
    public companion object : IntHolderCompanion<NonZeroInt>(::NonZeroInt) {
        private val negativeRange: IntRange = StrictlyNegativeInt.range
        private val positiveRange: IntRange = StrictlyPositiveInt.range
        private val ranges: Pair<IntRange, IntRange> =
            negativeRange to positiveRange
        override val min: NonZeroInt = builder(negativeRange.first)
        override val max: NonZeroInt = builder(positiveRange.last)
        override val random: NonZeroInt
            get() = ranges.toList()
                .random()
                .random()
                .let(builder)
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] equals `-1`, it returns `1` instead.
     * If this [value] is the [maximum][NonZeroInt.max], it returns the
     * [minimum][NonZeroInt.min] value instead.
     */
    public operator fun inc(): NonZeroInt = when (value) {
        -1 -> NonZeroInt(1)
        max.value -> min
        else -> NonZeroInt(value + 1)
    }

    /**
     * Returns this [value] decremented by one.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    public operator fun dec(): NonZeroInt = when (value) {
        1 -> NonZeroInt(-1)
        min.value -> max
        else -> NonZeroInt(value - 1)
    }

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NonZeroInt = NonZeroInt(-value)

    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: NonZeroInt): Int = super.compareTo(other)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a [PositiveInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = value.toPositiveInt()

    /**
     * Returns this [value] as a [PositiveInt], or returns `null` if this
     * [value] is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveInt? = value.toPositiveIntOrNull()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or throws an
     * [IllegalArgumentException] if this [value] is negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or returns `null` if
     * this [value] is negative.
     */
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        value.toStrictlyPositiveIntOrNull()

    /**
     * Returns this [value] as a [NegativeInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    /**
     * Returns this [value] as a [NegativeInt], or returns `null` if this
     * [value] is strictly positive.
     */
    public fun toNegativeIntOrNull(): NegativeInt? = value.toNegativeIntOrNull()

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or throws an
     * [IllegalArgumentException] if this [value] is positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or returns `null` if
     * this [value] is positive.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        value.toStrictlyNegativeIntOrNull()

    override fun toString(): String = value.toString()
}

internal object NonZeroIntSerializer :
    IntHolderSerializer<NonZeroInt>(Int::toNonZeroInt)

// ---------- PositiveInt ----------

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * strictly negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toPositiveInt(): PositiveInt = toInt().toPositiveInt()

/**
 * Returns this value as a [PositiveInt], or returns null if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly negative
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toPositiveIntOrNull(): PositiveInt? =
    toIntOrNull()?.toPositiveIntOrNull()

/**
 * Representation of positive integers, including zero.
 *
 * @constructor Returns the [value] as a [PositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@JvmInline
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class PositiveInt
@Throws(IllegalArgumentException::class)
constructor(override val value: Int) : Comparable<PositiveInt>,
    PositiveIntHolder {
    init {
        require(value >= 0) {
            "PositiveInt doesn't accept strictly negative values (tried with " +
                    "$value)."
        }
    }

    /** Contains static declarations for creating or holding a [PositiveInt]. */
    public companion object : IntHolderCompanion<PositiveInt>(::PositiveInt) {
        private val range: IntRange = 0..Int.MAX_VALUE
        override val min: PositiveInt = builder(range.first)
        override val max: PositiveInt = builder(range.last)
        override val random: PositiveInt get() = builder(range.random())
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][PositiveInt.max], it returns the
     * [minimum][PositiveInt.min] value instead.
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else PositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][PositiveInt.min], it returns the
     * [maximum][PositiveInt.max] value instead.
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else PositiveInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NegativeInt = NegativeInt(-value)

    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: PositiveInt): Int = super.compareTo(other)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a [NonZeroInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /**
     * Returns this [value] as a [NonZeroInt], or returns `null` if this [value]
     * equals zero.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = value.toNonZeroIntOrNull()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or returns `null` if
     * this [value] equals zero.
     */
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        value.toStrictlyPositiveIntOrNull()

    /**
     * Returns this [value] as a [NegativeInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    /**
     * Returns this [value] as a [NegativeInt], or returns `null` if this
     * [value] is strictly positive.
     */
    public fun toNegativeIntOrNull(): NegativeInt? = value.toNegativeIntOrNull()

    override fun toString(): String = value.toString()
}

internal object PositiveIntSerializer :
    IntHolderSerializer<PositiveInt>(Int::toPositiveInt)

// ---------- StrictlyPositiveInt ----------

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a [StrictlyPositiveInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    toIntOrNull()?.toStrictlyPositiveIntOrNull()

/**
 * Representation of strictly positive integers, excluding zero.
 *
 * @constructor Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
@Throws(IllegalArgumentException::class)
constructor(override val value: Int) : Comparable<StrictlyPositiveInt>,
    NonZeroIntHolder,
    PositiveIntHolder {
    init {
        require(value > 0) {
            "StrictlyPositiveInt doesn't accept negative values (tried with " +
                    "$value)."
        }
    }

    /**
     * Contains static declarations for creating or holding a
     * [StrictlyPositiveInt].
     */
    public companion object :
        IntHolderCompanion<StrictlyPositiveInt>(::StrictlyPositiveInt) {
        internal val range: IntRange = 1..Int.MAX_VALUE
        override val min: StrictlyPositiveInt = builder(range.first)
        override val max: StrictlyPositiveInt = builder(range.last)
        override val random: StrictlyPositiveInt get() = builder(range.random())
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][StrictlyPositiveInt.max], it returns
     * the [minimum][StrictlyPositiveInt.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else StrictlyPositiveInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][StrictlyPositiveInt.min], it returns
     * the [maximum][StrictlyPositiveInt.max] value instead.
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else StrictlyPositiveInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyNegativeInt =
        StrictlyNegativeInt(-value)

    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: StrictlyPositiveInt): Int =
        super<PositiveIntHolder>.compareTo(other)

    // ---------- Conversions ----------

    /** Returns this [value] as a [NonZeroInt]. */
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /** Returns this [value] as a [PositiveInt]. */
    public fun toPositiveInt(): PositiveInt = value.toPositiveInt()

    override fun toString(): String = value.toString()
}

internal object StrictlyPositiveIntSerializer :
    IntHolderSerializer<StrictlyPositiveInt>(Int::toStrictlyPositiveInt)

// ---------- NegativeInt ----------

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * strictly positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeInt(): NegativeInt = toInt().toNegativeInt()

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull this

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly positive
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNegativeIntOrNull(): NegativeInt? =
    toIntOrNull()?.toNegativeIntOrNull()

/**
 * Representation of negative integers, including zero.
 *
 * @constructor Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@JvmInline
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class NegativeInt
@Throws(IllegalArgumentException::class)
constructor(override val value: Int) : Comparable<NegativeInt>,
    NegativeIntHolder {
    init {
        require(value <= 0) {
            "NegativeInt doesn't accept strictly positive values (tried with " +
                    "$value)."
        }
    }

    /** Contains static declarations for creating or holding a [NegativeInt]. */
    public companion object : IntHolderCompanion<NegativeInt>(::NegativeInt) {
        private val range: IntRange = Int.MIN_VALUE..0
        override val min: NegativeInt = builder(range.first)
        override val max: NegativeInt = builder(range.last)
        override val random: NegativeInt get() = builder(range.random())
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][NegativeInt.max], it returns the
     * [minimum][NegativeInt.min] value instead.
     */
    public operator fun inc(): NegativeInt = if (value == max.value) min
    else NegativeInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][NegativeInt.min], it returns the
     * [maximum][NegativeInt.max] value instead.
     */
    public operator fun dec(): NegativeInt = if (value == min.value) max
    else NegativeInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): PositiveInt = PositiveInt(-value)

    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: NegativeInt): Int = super.compareTo(other)

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a [NonZeroInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /**
     * Returns this [value] as a [NonZeroInt], or returns `null` if this [value]
     * equals zero.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = value.toNonZeroIntOrNull()

    /**
     * Returns this [value] as a [PositiveInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = value.toPositiveInt()

    /**
     * Returns this [value] as a [PositiveInt], or returns `null` if this
     * [value] is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveInt? = value.toPositiveIntOrNull()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        value.toStrictlyNegativeInt()

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or returns `null` if
     * this [value] equals zero.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        value.toStrictlyNegativeIntOrNull()

    override fun toString(): String = value.toString()
}

internal object NegativeIntSerializer :
    IntHolderSerializer<NegativeInt>(Int::toNegativeInt)

// ---------- StrictlyNegativeInt ----------

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a [StrictlyNegativeInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toInt().toStrictlyNegativeInt()

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    StrictlyNegativeInt orNull this

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * positive number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    toIntOrNull()?.toStrictlyNegativeIntOrNull()

/**
 * Representation of strictly negative integers, excluding zero.
 *
 * @constructor Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is positive.
 */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
@Throws(IllegalArgumentException::class)
constructor(override val value: Int) : Comparable<StrictlyNegativeInt>,
    NegativeIntHolder,
    NonZeroIntHolder {
    init {
        require(value < 0) {
            "StrictlyNegativeInt doesn't accept positive values (tried with " +
                    "$value)."
        }
    }

    /**
     * Contains static declarations for creating or holding a
     * [StrictlyNegativeInt].
     */
    public companion object :
        IntHolderCompanion<StrictlyNegativeInt>(::StrictlyNegativeInt) {
        internal val range: IntRange = Int.MIN_VALUE..-1
        override val min: StrictlyNegativeInt = builder(range.first)
        override val max: StrictlyNegativeInt = builder(range.last)
        override val random: StrictlyNegativeInt get() = builder(range.random())
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][StrictlyNegativeInt.max], it returns
     * the [minimum][StrictlyNegativeInt.min] value instead.
     */
    public operator fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else StrictlyNegativeInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][StrictlyNegativeInt.min], it returns
     * the [maximum][StrictlyNegativeInt.max] value instead.
     */
    public operator fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else StrictlyNegativeInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyPositiveInt =
        StrictlyPositiveInt(-value)

    // ---------- Binary operations ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: StrictlyNegativeInt): Int =
        super<NegativeIntHolder>.compareTo(other)

    // ---------- Conversions ----------

    /** Returns this [value] as a [NonZeroInt]. */
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /** Returns this [value] as a [NegativeInt]. */
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    override fun toString(): String = value.toString()
}

internal object StrictlyNegativeIntSerializer :
    IntHolderSerializer<StrictlyNegativeInt>(Int::toStrictlyNegativeInt)
