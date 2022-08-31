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
 * Returns this value as a negative int, or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a negative int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * strictly positive number.
 */
@SinceKotoolsTypes("2.1")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeInt(): NegativeInt = toInt().toNegativeInt()

/**
 * Returns this value as a negative int, or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeInt orNull this

/**
 * Returns this value as a negative int, or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly positive
 * number.
 */
@SinceKotoolsTypes("2.1")
public fun String.toNegativeIntOrNull(): NegativeInt? =
    toIntOrNull()?.toNegativeIntOrNull()

/**
 * Represents negative integers, including `0`.
 *
 * @constructor Returns the [value] as a negative int, or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@JvmInline
@Serializable(NegativeInt.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class NegativeInt
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
     * If this [value] is the [maximum][NegativeInt.max], it returns the
     * [minimum][NegativeInt.min] value instead.
     */
    public operator fun inc(): NegativeInt = if (value == max.value) min
    else NegativeInt(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][NegativeInt.min], it returns the
     * [maximum][NegativeInt.max] value instead.
     */
    public operator fun dec(): NegativeInt = if (value == min.value) max
    else NegativeInt(value - 1)

    /** Returns this [value]. */
    public operator fun unaryPlus(): NegativeInt = this

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): PositiveInt = PositiveInt(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

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
     * Returns this [value] as a strictly negative int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a strictly negative int, or returns `null` if
     * this [value] equals `0`.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeInt orNull value

    /**
     * Returns this [value] as a positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /**
     * Returns this [value] as a positive int, or returns `null` if this [value]
     * is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveInt orNull value

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("2.1")
        internal val range: IntRange = Int.MIN_VALUE..0

        /** The minimum value of a negative int. */
        public val min: NegativeInt = NegativeInt(range.first)

        /** The maximum value of a negative int. */
        public val max: NegativeInt = NegativeInt(range.last)

        /**
         * Returns the [value] as a negative int, or returns `null` if the
         * [value] is strictly positive.
         */
        public infix fun orNull(value: Int): NegativeInt? = try {
            NegativeInt(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("2.1")
    internal object Serializer : KSerializer<NegativeInt> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            NegativeInt::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: NegativeInt): Unit =
            encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): NegativeInt =
            decoder.decodeInt().toNegativeInt()
    }
}
