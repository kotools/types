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
 * Returns this value as a negative int, or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeIntJvm(): NegativeIntJvm = NegativeIntJvm(this)

/**
 * Returns this value as a negative int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * strictly positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeIntJvm(): NegativeIntJvm =
    toInt().toNegativeIntJvm()

/**
 * Returns this value as a negative int, or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeIntJvmOrNull(): NegativeIntJvm? =
    NegativeIntJvm orNull this

/**
 * Returns this value as a negative int, or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly positive
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNegativeIntJvmOrNull(): NegativeIntJvm? =
    toIntOrNull()?.toNegativeIntJvmOrNull()

/**
 * Represents negative integers, including `0`.
 *
 * @constructor Returns the [value] as a negative int, or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@JvmInline
@Serializable(NegativeIntJvm.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class NegativeIntJvm
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
     * If this [value] is the [maximum][NegativeIntJvm.max], it returns the
     * [minimum][NegativeIntJvm.min] value instead.
     */
    public operator fun inc(): NegativeIntJvm = if (value == max.value) min
    else NegativeIntJvm(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][NegativeIntJvm.min], it returns the
     * [maximum][NegativeIntJvm.max] value instead.
     */
    public operator fun dec(): NegativeIntJvm = if (value == min.value) max
    else NegativeIntJvm(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): PositiveIntJvm = PositiveIntJvm(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveIntJvm): NegativeIntJvm =
        div(other.value).toNegativeIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(
        other: StrictlyPositiveIntJvm
    ): NegativeIntJvm = div(other.value).toNegativeIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeIntJvm): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(
        other: StrictlyNegativeIntJvm
    ): PositiveIntJvm = div(other.value).toPositiveIntJvm()

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a non-zero int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroIntJvm = NonZeroIntJvm(value)

    /**
     * Returns this [value] as a non-zero int, or returns `null` if this [value]
     * equals `0`.
     */
    public fun toNonZeroIntOrNull(): NonZeroIntJvm? = NonZeroIntJvm orNull value

    /**
     * Returns this [value] as a strictly negative int, or throws an
     * [IllegalArgumentException] if this [value] equals `0`.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeIntJvm =
        StrictlyNegativeIntJvm(value)

    /**
     * Returns this [value] as a strictly negative int, or returns `null` if
     * this [value] equals `0`.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeIntJvm? =
        StrictlyNegativeIntJvm orNull value

    /**
     * Returns this [value] as a positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveIntJvm = PositiveIntJvm(value)

    /**
     * Returns this [value] as a positive int, or returns `null` if this [value]
     * is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveIntJvm? =
        PositiveIntJvm orNull value

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("3.0")
        private val range: IntRange = Int.MIN_VALUE..0

        /** The minimum value of a negative int. */
        public val min: NegativeIntJvm = NegativeIntJvm(range.first)

        /** The maximum value of a negative int. */
        public val max: NegativeIntJvm = NegativeIntJvm(range.last)

        /** Returns a random negative int. */
        @SinceKotoolsTypes("3.0")
        public val random: NegativeIntJvm
            get() = range.random().toNegativeIntJvm()

        /**
         * Returns the [value] as a negative int, or returns `null` if the
         * [value] is strictly positive.
         */
        public infix fun orNull(value: Int): NegativeIntJvm? = try {
            NegativeIntJvm(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<NegativeIntJvm> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            NegativeIntJvm::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: NegativeIntJvm): Unit =
            encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): NegativeIntJvm =
            decoder.decodeInt().toNegativeIntJvm()
    }
}
