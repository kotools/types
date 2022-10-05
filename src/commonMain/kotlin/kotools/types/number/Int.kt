package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.Holder
import kotools.types.core.HolderCompanion
import kotools.types.core.SinceKotoolsTypes
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
public sealed class IntHolderCompanion<out T : IntHolder>(builder: (Int) -> T) :
    HolderCompanion<Int, T>(builder) {
    /** The minimum value of the type [T]. */
    public abstract val min: T

    /** The maximum value of the type [T]. */
    public abstract val max: T

    /** Returns an instance of type [T] holding a random value. */
    public abstract val random: T
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
