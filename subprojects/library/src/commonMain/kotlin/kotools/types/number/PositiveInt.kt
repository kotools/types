package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBePositive
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.InternalKotoolsTypesApi
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/**
 * Returns this number as an encapsulated [PositiveInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly negative][StrictlyNegativeInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toPositiveInt(): Result<PositiveInt> {
    val value: Int = toInt()
    return when {
        value == 0 -> Result.success(ZeroInt)
        value.isStrictlyPositive() -> value.toStrictlyPositiveInt()
        else -> {
            val message: ErrorMessage = value.shouldBePositive()
            val exception = IllegalArgumentException("$message")
            Result.failure(exception)
        }
    }
}

/**
 * Represents an integer number of type [Int] that is greater than or equals
 * zero.
 *
 * You can use the [toPositiveInt] function for creating an instance of this
 * type.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [Int] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * SAMPLE: [kotools.types.number.PositiveIntCommonSample.serialization]
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(PositiveIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy { StrictlyPositiveInt.max }

        /** Returns a random [PositiveInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): PositiveInt = (min.toInt()..max.toInt())
            .random()
            .toPositiveInt()
            .getOrThrow()
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.div(other: StrictlyPositiveInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.div(other: StrictlyNegativeInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.rem(other: NonZeroInt): PositiveInt {
    val result: Int = toInt() % other
    return result.toPositiveInt()
        .getOrThrow()
}

@InternalKotoolsTypesApi
internal object PositiveIntSerializer :
    KSerializer<PositiveInt> by intSerializer(
        PositiveIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object PositiveIntDeserializationStrategy :
    DeserializationStrategy<PositiveInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<PositiveInt>()
        val serialName = "kotools.types.number.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    override fun deserialize(decoder: Decoder): PositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toPositiveInt().getOrElse {
            val message: ErrorMessage = value.shouldBePositive()
            serializationError(message)
        }
    }
}
