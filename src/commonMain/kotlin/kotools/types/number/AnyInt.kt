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
@SinceKotoolsTypes("4.0")
public sealed interface AnyInt {
    /** Returns this integer as an [Int]. */
    public fun toInt(): Int

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
        encoder.encodeInt(value.toInt())

    fun deserialize(value: Int): I

    override fun deserialize(decoder: Decoder): I = decoder.decodeInt()
        .let(::deserialize)
}

internal object AnyIntSerializerImplementation : AnyIntSerializer<AnyInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.AnyInt"::toNotBlankString
    )

    override fun deserialize(value: Int): AnyInt = when {
        value == ZeroInt.toInt() -> Result.success(ZeroInt)
        value > ZeroInt.toInt() -> value.toStrictlyPositiveInt()
        else -> value.toStrictlyNegativeInt()
    }.getOrThrow()
}
