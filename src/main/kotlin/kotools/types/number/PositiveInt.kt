package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns this value as a positive int, or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a positive int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * strictly negative number.
 */
@SinceKotoolsTypes("2.1")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toPositiveInt(): PositiveInt = toInt().toPositiveInt()

/**
 * Returns this value as a positive int, or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull this

/**
 * Returns this value as a positive int, or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly negative
 * number.
 */
@SinceKotoolsTypes("2.1")
public fun String.toPositiveIntOrNull(): PositiveInt? =
    toIntOrNull()?.toPositiveIntOrNull()

/**
 * Represents positive integers, including `0`.
 *
 * @constructor Returns the [value] as a positive int, or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@JvmInline
@Serializable(PositiveInt.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class PositiveInt
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsInt {
    init {
        require(value in range) {
            val type: String = this::class.simpleName!!
            "$type accepts values included in $range (tried with $value)."
        }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][PositiveInt.max], it returns the
     * [minimum][PositiveInt.min] value instead.
     */
    public operator fun inc(): PositiveInt = if (value == max.value) min
    else PositiveInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][PositiveInt.min], it returns the
     * [maximum][PositiveInt.max] value instead.
     */
    public operator fun dec(): PositiveInt = if (value == min.value) max
    else PositiveInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): PositiveInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NegativeInt = NegativeInt(-value)

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: PositiveInt): PositiveInt =
        PositiveInt(plus(other.value))

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(
        other: StrictlyPositiveInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(plus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: NegativeInt): PositiveInt =
        PositiveInt(minus(other.value))

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(
        other: StrictlyNegativeInt
    ): StrictlyPositiveInt = StrictlyPositiveInt(minus(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: PositiveInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NegativeInt): NegativeInt =
        NegativeInt(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(times(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(div(other.value))

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a non-zero int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /**
     * Returns this [value] as a non-zero int, or returns `null` if this [value]
     * equals `0`.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = NonZeroInt orNull value

    /**
     * Returns this [value] as a strictly positive int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        StrictlyPositiveInt(value)

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] equals `0`.
     */
    public fun toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
        StrictlyPositiveInt orNull value

    /**
     * Returns this [value] as a negative int, or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNegativeInt(): NegativeInt = NegativeInt(value)

    /**
     * Returns this [value] as a negative int, or returns `null` if this [value]
     * is strictly positive.
     */
    public fun toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull value

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("2.1")
        internal val range: IntRange = 0..Int.MAX_VALUE

        /** The minimum value of a positive int. */
        public val min: PositiveInt = PositiveInt(range.first)

        /** The maximum value of a positive int. */
        public val max: PositiveInt = PositiveInt(range.last)

        /**
         * Returns the [value] as a positive int, or returns `null` if the
         * [value] is strictly negative.
         */
        public infix fun orNull(value: Int): PositiveInt? = try {
            PositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("2.1")
    internal object Serializer : KSerializer<PositiveInt> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            PositiveInt::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: PositiveInt): Unit =
            encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): PositiveInt =
            decoder.decodeInt().toPositiveInt()
    }
}
