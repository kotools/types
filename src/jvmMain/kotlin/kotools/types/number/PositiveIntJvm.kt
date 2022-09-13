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
 * Returns this value as a positive int, or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveIntJvm(): PositiveIntJvm = PositiveIntJvm(this)

/**
 * Returns this value as a positive int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws a [IllegalArgumentException] if it represents a
 * strictly negative number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toPositiveIntJvm(): PositiveIntJvm =
    toInt().toPositiveIntJvm()

/**
 * Returns this value as a positive int, or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntJvmOrNull(): PositiveIntJvm? =
    PositiveIntJvm orNull this

/**
 * Returns this value as a positive int, or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly negative
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toPositiveIntJvmOrNull(): PositiveIntJvm? =
    toIntOrNull()?.toPositiveIntJvmOrNull()

/**
 * Represents positive integers, including `0`.
 *
 * @constructor Returns the [value] as a positive int, or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@JvmInline
@Serializable(PositiveIntJvm.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class PositiveIntJvm
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsIntJvm {
    init {
        require(value in range) {
            val type: String = this::class.simpleName!!
            "$type accepts values included in $range (tried with $value)."
        }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] is the [maximum][PositiveIntJvm.max], it returns the
     * [minimum][PositiveIntJvm.min] value instead.
     */
    public operator fun inc(): PositiveIntJvm = if (value == max.value) min
    else PositiveIntJvm(value + 1)

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] is the [minimum][PositiveIntJvm.min], it returns the
     * [maximum][PositiveIntJvm.max] value instead.
     */
    public operator fun dec(): PositiveIntJvm = if (value == min.value) max
    else PositiveIntJvm(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NegativeInt = NegativeInt(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: PositiveIntJvm): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): PositiveIntJvm =
        div(other.value).toPositiveIntJvm()

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     * Throws an [ArithmeticException] if the [other] value equals `0`.
     */
    @Throws(ArithmeticException::class)
    public infix operator fun div(other: NegativeInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by [other], truncating the result to an integer that
     * is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): NegativeInt =
        div(other.value).toNegativeInt()

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
        @SinceKotoolsTypes("3.0")
        private val range: IntRange = 0..Int.MAX_VALUE

        /** The minimum value of a positive int. */
        public val min: PositiveIntJvm = PositiveIntJvm(range.first)

        /** The maximum value of a positive int. */
        public val max: PositiveIntJvm = PositiveIntJvm(range.last)

        /** Returns a random positive int. */
        @SinceKotoolsTypes("3.0")
        public val random: PositiveIntJvm
            get() = range.random().toPositiveIntJvm()

        /**
         * Returns the [value] as a positive int, or returns `null` if the
         * [value] is strictly negative.
         */
        public infix fun orNull(value: Int): PositiveIntJvm? = try {
            PositiveIntJvm(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<PositiveIntJvm> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            PositiveIntJvm::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: PositiveIntJvm): Unit =
            encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): PositiveIntJvm =
            decoder.decodeInt().toPositiveIntJvm()
    }
}
