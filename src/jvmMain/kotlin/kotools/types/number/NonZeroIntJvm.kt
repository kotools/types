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
 * Returns this value as a non-zero int, or throws an [IllegalArgumentException]
 * if this value equals `0`.
 */
@SinceKotoolsTypes("1.1")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroIntJvm(): NonZeroIntJvm = NonZeroIntJvm(this)

/**
 * Returns this value as a non-zero int.
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNonZeroIntJvm(): NonZeroIntJvm = toInt().toNonZeroIntJvm()

/**
 * Returns this value as a non-zero int, or returns `null` if this value equals
 * `0`.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroIntJvmOrNull(): NonZeroIntJvm? =
    NonZeroIntJvm orNull this

/**
 * Returns this value as a non-zero int, or returns `null` if this value is not
 * a valid representation of a number or if it represents `0`.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNonZeroIntJvmOrNull(): NonZeroIntJvm? =
    toIntOrNull()?.toNonZeroIntJvmOrNull()

/**
 * Represents integers that don't equal `0`.
 *
 * @constructor Returns the [value] as a non-zero int, or throws an
 * [IllegalArgumentException] if the [value] equals `0`.
 */
@JvmInline
@Serializable(NonZeroIntJvm.Serializer::class)
@SinceKotoolsTypes("1.1")
public value class NonZeroIntJvm
@Throws(IllegalArgumentException::class)
public constructor(override val value: Int) : KotoolsInt {
    init {
        val valueIsInRanges: Boolean = ranges.any { value in it }
        require(valueIsInRanges) {
            val type: String = this::class.simpleName!!
            "$type doesn't accept 0."
        }
    }

    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by `1`.
     * If this [value] equals `-1`, it returns `1` instead.
     * If this [value] is the [maximum][NonZeroIntJvm.max], it returns the
     * [minimum][NonZeroIntJvm.min] value instead.
     */
    public operator fun inc(): NonZeroIntJvm = when (value) {
        -1 -> NonZeroIntJvm(1)
        max.value -> min
        else -> NonZeroIntJvm(value + 1)
    }

    /**
     * Returns this [value] decremented by `1`.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroIntJvm.min], it returns the
     * [maximum][NonZeroIntJvm.max] value instead.
     */
    public operator fun dec(): NonZeroIntJvm = when (value) {
        1 -> NonZeroIntJvm(-1)
        min.value -> max
        else -> NonZeroIntJvm(value - 1)
    }

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): NonZeroIntJvm = NonZeroIntJvm(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: NonZeroIntJvm): NonZeroIntJvm =
        NonZeroIntJvm(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyPositiveInt): NonZeroIntJvm =
        NonZeroIntJvm(times(other.value))

    /** Multiplies this [value] by the [other] value. */
    public infix operator fun times(other: StrictlyNegativeInt): NonZeroIntJvm =
        NonZeroIntJvm(times(other.value))

    // ---------- Conversions ----------

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

    /**
     * Returns this [value] as a strictly positive int, or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyPositiveInt(): StrictlyPositiveInt =
        StrictlyPositiveInt(value)

    /**
     * Returns this [value] as a strictly positive int, or returns `null` if
     * this [value] is strictly negative.
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

    /**
     * Returns this [value] as a strictly negative int, or throws an
     * [IllegalArgumentException] if this [value] is strictly positive.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a strictly negative int, or returns `null` if
     * this [value] is strictly positive.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeInt orNull value

    override fun toString(): String = value.toString()

    public companion object {
        @SinceKotoolsTypes("3.0")
        internal val negativeRange: IntRange = StrictlyNegativeInt.range

        @SinceKotoolsTypes("3.0")
        internal val positiveRange: IntRange = StrictlyPositiveInt.range

        @SinceKotoolsTypes("3.0")
        internal val ranges: Set<IntRange> = setOf(negativeRange, positiveRange)

        /** The minimum value of a non-zero int. */
        public val min: NonZeroIntJvm = NonZeroIntJvm(negativeRange.first)

        /** The maximum value of a non-zero int. */
        public val max: NonZeroIntJvm = NonZeroIntJvm(positiveRange.last)

        /** Returns a random non-zero int. */
        @SinceKotoolsTypes("3.0")
        public val random: NonZeroIntJvm
            get() = ranges.random()
                .random()
                .toNonZeroIntJvm()

        /**
         * Returns the [value] as a non-zero int, or returns `null` if the
         * [value] equals `0`.
         */
        public infix fun orNull(value: Int): NonZeroIntJvm? = try {
            NonZeroIntJvm(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    @SinceKotoolsTypes("3.0")
    internal object Serializer : KSerializer<NonZeroIntJvm> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            NonZeroIntJvm::class.qualifiedName!!,
            PrimitiveKind.INT
        )

        override fun serialize(encoder: Encoder, value: NonZeroIntJvm): Unit =
            encoder.encodeInt(value.value)

        override fun deserialize(decoder: Decoder): NonZeroIntJvm =
            decoder.decodeInt().toNonZeroIntJvm()
    }
}
