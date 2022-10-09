package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt], or returns null if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

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
    /** Contains static declarations for creating or holding a [PositiveInt]. */
    public companion object : IntHolderCompanion<PositiveInt>(::PositiveInt) {
        private val range: IntRange = 0..Int.MAX_VALUE
        override val min: PositiveInt = builder(range.first)
        override val max: PositiveInt = builder(range.last)
        override val random: PositiveInt get() = builder(range.random())
    }

    init {
        require(value in range) {
            "PositiveInt accepts values in $range (tried with $value)."
        }
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
    @Deprecated(
        "Use the Int.toNonZeroInt function instead.",
        ReplaceWith(
            "this.value.toNonZeroInt()",
            "kotools.types.number.toNonZeroInt"
        )
    )
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroInt()

    /**
     * Returns this [value] as a [NonZeroInt], or returns `null` if this [value]
     * equals zero.
     */
    @Deprecated(
        "Use the Int.toNonZeroIntOrNull function instead.",
        ReplaceWith(
            "this.value.toNonZeroIntOrNull()",
            "kotools.types.number.toNonZeroIntOrNull"
        )
    )
    public fun toNonZeroIntOrNull(): NonZeroInt? = value.toNonZeroIntOrNull()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Deprecated(
        "Use the Int.toStrictlyPositiveInt function instead.",
        ReplaceWith(
            "this.value.toStrictlyPositiveInt()",
            "kotools.types.number.toStrictlyPositiveInt"
        )
    )
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        value.toStrictlyPositiveInt()

    /**
     * Returns this [value] as a [StrictlyPositiveInt], or returns `null` if
     * this [value] equals zero.
     */
    @Deprecated(
        "Use the Int.toStrictlyPositiveIntOrNull function instead.",
        ReplaceWith(
            "this.value.toStrictlyPositiveIntOrNull()",
            "kotools.types.number.toStrictlyPositiveIntOrNull"
        )
    )
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        value.toStrictlyPositiveIntOrNull()

    /**
     * Returns this [value] as a [NegativeInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Deprecated(
        "Use the Int.toNegativeInt function instead.",
        ReplaceWith(
            "this.value.toNegativeInt()",
            "kotools.types.number.toNegativeInt"
        )
    )
    public fun toNegativeInt(): NegativeInt = value.toNegativeInt()

    /**
     * Returns this [value] as a [NegativeInt], or returns `null` if this
     * [value] is strictly positive.
     */
    @Deprecated(
        "Use the Int.toNegativeIntOrNull function instead.",
        ReplaceWith(
            "this.value.toNegativeIntOrNull()",
            "kotools.types.number.toNegativeIntOrNull"
        )
    )
    public fun toNegativeIntOrNull(): NegativeInt? = value.toNegativeIntOrNull()

    override fun toString(): String = value.toString()
}

internal object PositiveIntSerializer :
    IntHolderSerializer<PositiveInt>(Int::toPositiveInt)
