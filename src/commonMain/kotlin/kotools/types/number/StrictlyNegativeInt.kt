package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline


/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    StrictlyNegativeInt orNull this

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

    init {
        require(value < 0) {
            "StrictlyNegativeInt doesn't accept positive values (tried with " +
                    "$value)."
        }
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
