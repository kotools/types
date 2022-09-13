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
@SinceKotoolsTypes("3.0")
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
@SinceKotoolsTypes("3.0")
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
        require(value in range) {
            val type: String = this::class.simpleName!!
            "$type accepts values included in $range (tried with $value)."
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

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyPositiveInt =
        StrictlyPositiveInt(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroIntJvm): NonZeroIntJvm =
        times(other.value).toNonZeroIntJvm()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroIntJvm =
        times(other.value).toNonZeroIntJvm()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroIntJvm =
        times(other.value).toNonZeroIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveIntJvm): NegativeInt =
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
    public infix operator fun div(other: NegativeInt): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    // ---------- Conversions ----------

    /** Returns this [value] as a non-zero int. */
    public fun toNonZeroInt(): NonZeroIntJvm = NonZeroIntJvm(value)

    /** Returns this [value] as a negative int. */
    public fun toNegativeInt(): NegativeInt = NegativeInt(value)

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("3.0")
        internal val range: IntRange = NegativeInt.min.value..-1

        /** The minimum value of a strictly negative int. */
        public val min: StrictlyNegativeInt = StrictlyNegativeInt(range.first)

        /** The maximum value of a strictly negative int. */
        public val max: StrictlyNegativeInt = StrictlyNegativeInt(range.last)

        /** Returns a random strictly negative int. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyNegativeInt
            get() = range.random().toStrictlyNegativeInt()

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

    @SinceKotoolsTypes("3.0")
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
