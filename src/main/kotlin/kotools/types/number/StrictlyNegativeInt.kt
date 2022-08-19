package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.plus(other: StrictlyNegativeInt): Int =
    this + other.value

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.minus(other: StrictlyNegativeInt): Int =
    this - other.value

/** Multiplies this value by the [other] value. */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.times(other: StrictlyNegativeInt): Int =
    this * other.value

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 */
@SinceKotoolsTypes("1.1")
public infix operator fun Int.div(other: StrictlyNegativeInt): Int =
    this / other.value

// ---------- Conversions ----------

/**
 * Returns this value as a strictly negative int, or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a strictly negative int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * positive number.
 */
@SinceKotoolsTypes("2.1")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyNegativeInt(): StrictlyNegativeInt =
    toInt().toStrictlyNegativeInt()

/**
 * Returns this value as a strictly negative int, or returns `null` if this
 * value is positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    StrictlyNegativeInt orNull this

/**
 * Returns this value as a strictly negative int, or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * positive number.
 */
@SinceKotoolsTypes("2.1")
public fun String.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    toIntOrNull()?.toStrictlyNegativeIntOrNull()

/**
 * Represents strictly negative integers, excluding `0`.
 *
 * @constructor Returns the [value] as a strictly negative int, or throws an
 * [IllegalArgumentException] if the [value] is positive.
 */
@JvmInline
@Serializable(StrictlyNegativeInt.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsInt {
    init {
        require(value < 0) {
            val type: String = this::class.simpleName!!
            "$type doesn't accept positive values (tried with $value)."
        }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][StrictlyNegativeInt.max], it returns the
     * [minimum][StrictlyNegativeInt.min] value instead.
     */
    public operator fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else StrictlyNegativeInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][StrictlyNegativeInt.min], it returns the
     * [maximum][StrictlyNegativeInt.max] value instead.
     */
    public operator fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else StrictlyNegativeInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): StrictlyNegativeInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyPositiveInt =
        StrictlyPositiveInt(-value)

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NonZeroInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): Int = plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: StrictlyPositiveInt): Int =
        plus(other.value)

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: NegativeInt): StrictlyNegativeInt =
        StrictlyNegativeInt(plus(other.value))

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(
        other: StrictlyNegativeInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(plus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NonZeroInt): Int = minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: PositiveInt): StrictlyNegativeInt =
        StrictlyNegativeInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyPositiveInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): Int =
        minus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: StrictlyNegativeInt): Int =
        minus(other.value)

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: Int): Int = value * other

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        NonZeroInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyPositiveInt
    ): StrictlyNegativeInt = StrictlyNegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyNegativeInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(times(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: Int): Int = value / other

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: NonZeroInt): Int = div(other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(div(other.value))

    // ---------- Conversions ----------

    /** Returns this [value] as a non-zero int. */
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a negative int. */
    public fun toNegativeInt(): NegativeInt = NegativeInt(value)

    override fun toString(): String = value.toString()

    public companion object {
        /** The minimum value of a strictly negative int. */
        public val min: StrictlyNegativeInt = StrictlyNegativeInt(Int.MIN_VALUE)

        /** The maximum value of a strictly negative int. */
        public val max: StrictlyNegativeInt = StrictlyNegativeInt(-1)

        /**
         * Returns the [value] as a strictly negative int, or returns `null` if
         * the [value] is positive.
         */
        public infix fun orNull(value: Int): StrictlyNegativeInt? = try {
            StrictlyNegativeInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("2.1")
    internal object Serializer : KSerializer<StrictlyNegativeInt> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            StrictlyNegativeInt::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(
            encoder: Encoder,
            value: StrictlyNegativeInt
        ): Unit = encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): StrictlyNegativeInt =
            decoder.decodeInt().toStrictlyNegativeInt()
    }
}
