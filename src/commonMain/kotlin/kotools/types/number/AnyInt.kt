package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.NUMBER_PACKAGE
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of all integers. */
@Serializable(AnyIntSerializerImplementation::class)
@SinceKotoolsTypes("4.0")
public sealed interface AnyInt : Comparable<AnyInt> {
    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    @SinceKotoolsTypes("4.1")
    override infix fun compareTo(other: AnyInt): Int {
        val x: Int = toInt()
        val y: Int = other.toInt()
        return x.compareTo(y)
    }

    /** Returns this integer as an [Int]. */
    public fun toInt(): Int

    /** Returns the string representation of this integer. */
    override fun toString(): String
}

/** Returns the negative of this integer. */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
public operator fun AnyInt.unaryMinus(): Int = -toInt()

/** Adds the [other] integer to this one. */
@SinceKotoolsTypes("4.1")
public operator fun Int.plus(other: AnyInt): Int = this + other.toInt()

/** Adds the [other] integer to this one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.plus(other: Int): Int = toInt() + other

/** Adds the [other] integer to this one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.plus(other: AnyInt): Int = toInt() + other

/** Subtracts the [other] integer from this one. */
@SinceKotoolsTypes("4.1")
public operator fun Int.minus(other: AnyInt): Int = this - other.toInt()

/** Subtracts the [other] integer from this one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.minus(other: Int): Int = toInt() - other

/** Subtracts the [other] integer from this one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.minus(other: AnyInt): Int = toInt() - other

/** Multiplies this integer by the [other] one. */
@SinceKotoolsTypes("4.1")
public operator fun Int.times(other: AnyInt): Int = this * other.toInt()

/** Multiplies this integer by the [other] one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.times(other: Int): Int = toInt() * other

/** Multiplies this integer by the [other] one. */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.times(other: AnyInt): Int = toInt() * other

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.div(other: NonZeroInt): Int = toInt() / other

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@SinceKotoolsTypes("4.1")
public operator fun AnyInt.rem(other: NonZeroInt): Int = toInt() % other

internal sealed interface AnyIntSerializer<I : AnyInt> : KSerializer<I> {
    val serialName: Result<NotBlankString>

    override val descriptor: SerialDescriptor
        get() = serialName
            .map { PrimitiveSerialDescriptor("$it", PrimitiveKind.INT) }
            .getOrThrow()

    override fun serialize(encoder: Encoder, value: I): Unit =
        encoder.encodeInt(value.toInt())

    fun deserialize(value: Int): I

    override fun deserialize(decoder: Decoder): I = decoder.decodeInt()
        .let(::deserialize)
}

internal object AnyIntSerializerImplementation : AnyIntSerializer<AnyInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "$NUMBER_PACKAGE.AnyInt".toNotBlankString()
    }

    override fun deserialize(value: Int): AnyInt = when {
        value == ZeroInt.toInt() -> Result.success(ZeroInt)
        value > ZeroInt.toInt() -> value.toStrictlyPositiveInt()
        else -> value.toStrictlyNegativeInt()
    }.getOrThrow()
}
