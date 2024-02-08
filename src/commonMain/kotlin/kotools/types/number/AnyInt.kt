package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.simpleNameOf
import kotools.types.internal.unexpectedCreationError

/** Represents an integer number of type [Int]. */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(AnyIntSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public sealed interface AnyInt : Comparable<AnyInt> {
    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    @Since(KotoolsTypesVersion.V4_1_0)
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

/** Adds the [other] integer to this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.plus(other: AnyInt): Int = this + other.toInt()

/** Adds the [other] integer to this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.plus(other: Int): Int = toInt() + other

/** Adds the [other] integer to this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.plus(other: AnyInt): Int = toInt() + other

/** Subtracts the [other] integer from this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.minus(other: AnyInt): Int = this - other.toInt()

/** Subtracts the [other] integer from this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.minus(other: Int): Int = toInt() - other

/** Subtracts the [other] integer from this one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.minus(other: AnyInt): Int = toInt() - other

/** Multiplies this integer by the [other] one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.times(other: AnyInt): Int = this * other.toInt()

/** Multiplies this integer by the [other] one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.times(other: Int): Int = toInt() * other

/** Multiplies this integer by the [other] one. */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.times(other: AnyInt): Int = toInt() * other

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.div(other: NonZeroInt): Int = toInt() / other

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun AnyInt.rem(other: NonZeroInt): Int = toInt() % other

@InternalKotoolsTypesApi
internal object AnyIntSerializer : KSerializer<AnyInt> by intSerializer(
    AnyIntDeserializationStrategy,
    intConverter = { it.toInt() }
)

@InternalKotoolsTypesApi
private object AnyIntDeserializationStrategy : DeserializationStrategy<AnyInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<AnyInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): AnyInt {
        val value: Int = decoder.decodeInt()
        if (value == 0) return ZeroInt
        val result: Result<AnyInt> =
            if (value > 0) value.toStrictlyPositiveInt()
            else value.toStrictlyNegativeInt()
        return result.getOrNull() ?: unexpectedCreationError<AnyInt>(value)
    }
}
