package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

/** Represents strictly positive floating-point numbers represented by the [Double] type. */
@Serializable(StrictlyPositiveDoubleSerializer::class)
@SinceKotoolsTypes("4.2")
public sealed interface StrictlyPositiveDouble :
    Comparable<StrictlyPositiveDouble> {
    /**
     * Compares this floating-point number with the other one for order.
     * Returns zero if this floating-point number equals the other one,
     * a negative number if it's less than the other one,
     * or a positive number if it's greater than the other one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int {
        val x: Double = toDouble()
        val y: Double = other.toDouble()
        return x.compareTo(y)
    }

    /** Returns this floating-point number as a [Double]. */
    public fun toDouble(): Double

    /** Returns the string representation of this floating-point number. */
    override fun toString(): String
}

@JvmInline
private value class StrictlyPositiveDoubleImplementation(
    private val value: Double
) : StrictlyPositiveDouble {
    override fun toDouble(): Double = value
    override fun toString(): String = "$value"
}

/**
 * Returns this number as an encapsulated [StrictlyPositiveDouble], which may involve rounding or truncation,
 * or returns an encapsulated [IllegalArgumentException] if this number is negative.
 */
@SinceKotoolsTypes("4.2")
public fun Number.toStrictlyPositiveDouble(): Result<StrictlyPositiveDouble> =
    runCatching {
        val value: Double = toDouble()
        require(value > 0.0) { value shouldBe aStrictlyPositiveNumber }
        StrictlyPositiveDoubleImplementation(value)
    }

internal object StrictlyPositiveDoubleSerializer :
    KSerializer<StrictlyPositiveDouble> {
    override val descriptor: SerialDescriptor by lazy {
        PrimitiveSerialDescriptor(
            serialName = "${Package.number}.StrictlyPositiveDouble",
            kind = PrimitiveKind.DOUBLE
        )
    }

    override fun serialize(
        encoder: Encoder,
        value: StrictlyPositiveDouble
    ): Unit = value.toDouble()
        .let(encoder::encodeDouble)

    override fun deserialize(decoder: Decoder): StrictlyPositiveDouble {
        val value: Double = decoder.decodeDouble()
        return value.toStrictlyPositiveDouble()
            .getOrNull()
            ?: throw SerializationException(
                value shouldBe aStrictlyPositiveNumber
            )
    }
}
