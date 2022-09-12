package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes

// ---------- Builders ----------

/**
 * Returns this value as a strictly positive int, or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a strictly positive int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyPositiveInt(): StrictlyPositiveInt =
    toInt().toStrictlyPositiveInt()

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    StrictlyPositiveInt orNull this

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    toIntOrNull()?.toStrictlyPositiveIntOrNull()

/**
 * Represents strictly positive integers, excluding `0`.
 *
 * @constructor Returns the [value] as a strictly positive int, or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@JvmInline
@Serializable(StrictlyPositiveInt.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsJvmInt {
    init {
        require(value in range) {
            val type: String = this::class.simpleName!!
            "$type accepts values included in $range (tried with $value)."
        }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][StrictlyPositiveInt.max], it returns the
     * [minimum][StrictlyPositiveInt.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else StrictlyPositiveInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][StrictlyPositiveInt.min], it returns the
     * [maximum][StrictlyPositiveInt.max] value instead.
     */
    public operator fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else StrictlyPositiveInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyNegativeInt =
        StrictlyNegativeInt(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroInt =
        times(other.value).toNonZeroInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): PositiveInt =
        div(other.value).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveInt =
        div(other.value).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        div(other.value).toNegativeInt()

    // ---------- Conversions ----------

    /** Returns this [value] as a non-zero int. */
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /** Returns this [value] as a positive int. */
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("3.0")
        internal val range: IntRange = 1..PositiveInt.max.value

        /** The minimum value of a strictly positive int. */
        public val min: StrictlyPositiveInt = StrictlyPositiveInt(range.first)

        /** The maximum value of a strictly positive int. */
        public val max: StrictlyPositiveInt = StrictlyPositiveInt(range.last)

        /** Returns a random strictly positive int. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyPositiveInt
            get() = range.random().toStrictlyPositiveInt()

        /**
         * Returns the [value] as a strictly positive int, or returns `null` if
         * the [value] is negative.
         */
        public infix fun orNull(value: Int): StrictlyPositiveInt? = try {
            StrictlyPositiveInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<StrictlyPositiveInt> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            StrictlyPositiveInt::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(
            encoder: Encoder,
            value: StrictlyPositiveInt
        ): Unit = encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): StrictlyPositiveInt =
            decoder.decodeInt().toStrictlyPositiveInt()
    }
}
