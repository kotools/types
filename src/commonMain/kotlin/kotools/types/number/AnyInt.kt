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
import kotools.types.text.toNotBlankString

/** Representation of all integers. */
@Serializable(AnyIntSerializerImplementation::class)
@SinceKotoolsTypes("0.1")
public sealed interface AnyInt : Comparable<AnyInt> {
    /** The value to hold. */
    public val value: Int

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if it's less than the [other] value, or a positive number if it's greater
     * than the [other] value.
     */
    override fun compareTo(other: AnyInt): Int = value.compareTo(other.value)

    /** Returns this [value] as a [String]. */
    override fun toString(): String
}

internal sealed interface AnyIntSerializer<I : AnyInt> : KSerializer<I> {
    val serialName: Result<NotBlankString>

    override val descriptor: SerialDescriptor
        get() = serialName
            .map { PrimitiveSerialDescriptor(it.value, PrimitiveKind.INT) }
            .getOrThrow()

    override fun serialize(encoder: Encoder, value: I): Unit =
        encoder.encodeInt(value.value)

    fun deserialize(value: Int): I

    override fun deserialize(decoder: Decoder): I = deserialize(
        decoder.decodeInt()
    )
}

internal object AnyIntSerializerImplementation : AnyIntSerializer<AnyInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.AnyInt"::toNotBlankString
    )

    override fun deserialize(value: Int): AnyInt = when {
        value == ZeroInt.value -> Result.success(ZeroInt)
        value > ZeroInt.value -> value.toStrictlyPositiveInt()
        else -> value.toStrictlyNegativeInt()
    }.getOrThrow()
}
