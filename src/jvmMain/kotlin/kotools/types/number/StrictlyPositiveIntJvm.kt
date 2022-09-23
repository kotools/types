package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.SinceKotoolsTypes

// ---------- Builders ----------

/**
 * Returns this value as a strictly positive int, or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveIntJvm(): StrictlyPositiveIntJvm =
    StrictlyPositiveIntJvm(this)

/**
 * Returns this value as a strictly positive int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toStrictlyPositiveIntJvm(): StrictlyPositiveIntJvm =
    toInt().toStrictlyPositiveIntJvm()

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveIntJvmOrNull(): StrictlyPositiveIntJvm? =
    StrictlyPositiveIntJvm orNull this

/**
 * Returns this value as a strictly positive int, or returns `null` if this
 * value is not a valid representation of a number or if it represents a
 * negative number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toStrictlyPositiveIntJvmOrNull(): StrictlyPositiveIntJvm? =
    toIntOrNull()?.toStrictlyPositiveIntJvmOrNull()

/**
 * Represents strictly positive integers, excluding `0`.
 *
 * @constructor Returns the [value] as a strictly positive int, or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@JvmInline
@Serializable(StrictlyPositiveIntJvm.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveIntJvm
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
     * If this [value] is the [maximum][StrictlyPositiveIntJvm.max], it returns
     * the [minimum][StrictlyPositiveIntJvm.min] value instead.
     */
    public operator fun inc(): StrictlyPositiveIntJvm =
        if (value == max.value) min
        else StrictlyPositiveIntJvm(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][StrictlyPositiveIntJvm.min], it returns
     * the [maximum][StrictlyPositiveIntJvm.max] value instead.
     */
    public operator fun dec(): StrictlyPositiveIntJvm =
        if (value == min.value) max
        else StrictlyPositiveIntJvm(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): StrictlyNegativeIntJvm =
        StrictlyNegativeIntJvm(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroIntJvm): NonZeroIntJvm =
        times(other.value).toNonZeroIntJvm()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyPositiveIntJvm
    ): NonZeroIntJvm = times(other.value).toNonZeroIntJvm()

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(
        other: StrictlyNegativeIntJvm
    ): NonZeroIntJvm = times(other.value).toNonZeroIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveIntJvm): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(
        other: StrictlyPositiveIntJvm
    ): PositiveIntJvm = div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeIntJvm): NegativeIntJvm =
        div(other.value).toNegativeIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(
        other: StrictlyNegativeIntJvm
    ): NegativeIntJvm = div(other.value).toNegativeIntJvm()

    // ---------- Conversions ----------

    /** Returns this [value] as a non-zero int. */
    public fun toNonZeroInt(): NonZeroIntJvm = NonZeroIntJvm(value)

    /** Returns this [value] as a positive int. */
    public fun toPositiveInt(): PositiveIntJvm = PositiveIntJvm(value)

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("3.0")
        internal val range: IntRange = 1..PositiveIntJvm.max.value

        /** The minimum value of a strictly positive int. */
        public val min: StrictlyPositiveIntJvm =
            StrictlyPositiveIntJvm(range.first)

        /** The maximum value of a strictly positive int. */
        public val max: StrictlyPositiveIntJvm =
            StrictlyPositiveIntJvm(range.last)

        /** Returns a random strictly positive int. */
        @SinceKotoolsTypes("3.0")
        public val random: StrictlyPositiveIntJvm
            get() = range.random().toStrictlyPositiveIntJvm()

        /**
         * Returns the [value] as a strictly positive int, or returns `null` if
         * the [value] is negative.
         */
        public infix fun orNull(value: Int): StrictlyPositiveIntJvm? = try {
            StrictlyPositiveIntJvm(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<StrictlyPositiveIntJvm> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            StrictlyPositiveIntJvm::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(
            encoder: Encoder,
            value: StrictlyPositiveIntJvm
        ): Unit = encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): StrictlyPositiveIntJvm =
            decoder.decodeInt().toStrictlyPositiveIntJvm()
    }
}
