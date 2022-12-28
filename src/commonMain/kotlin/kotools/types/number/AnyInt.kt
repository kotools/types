package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/** Representation of all integers. */
@Serializable(AnyIntSerializerImplementation::class)
@SinceKotoolsTypes("4.0")
public sealed interface AnyInt : Comparable<AnyInt> {
    /** Returns this integer as an [Int]. */
    public val asInt: Int

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: AnyInt): Int = asInt.compareTo(other.asInt)

    /** Returns the string representation of this integer. */
    override fun toString(): String
}

internal sealed interface AnyIntSerializer<I : AnyInt> : KSerializer<I> {
    val serialName: Result<NotBlankString>

    override val descriptor: SerialDescriptor
        get() = serialName
            .map { PrimitiveSerialDescriptor("$it", PrimitiveKind.INT) }
            .getOrThrow()

    override fun serialize(encoder: Encoder, value: I): Unit =
        encoder.encodeInt(value.asInt)

    fun deserialize(value: Int): I

    override fun deserialize(decoder: Decoder): I = deserialize(
        decoder.decodeInt()
    )
}

internal object AnyIntSerializerImplementation : AnyIntSerializer<AnyInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.AnyInt"::asNotBlankString
    )

    override fun deserialize(value: Int): AnyInt = when {
        value == ZeroInt.asInt -> Result.success(ZeroInt)
        value > ZeroInt.asInt -> value.toStrictlyPositiveInt()
        else -> value.toStrictlyNegativeInt()
    }.getOrThrow()
}
