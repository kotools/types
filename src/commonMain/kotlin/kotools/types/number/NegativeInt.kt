package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

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
