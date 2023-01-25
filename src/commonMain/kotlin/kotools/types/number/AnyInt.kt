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

/** Adds the [other] integer to this one. */
internal operator fun AnyInt.plus(other: Int): AnyInt = (toInt() + other)
    .toAnyInt()

/** Returns this integer as an [AnyInt]. */
private fun Int.toAnyInt(): AnyInt = when {
    this == ZeroInt.toInt() -> Result.success(ZeroInt)
    this > ZeroInt.toInt() -> toStrictlyPositiveInt()
    else -> toStrictlyNegativeInt()
}.getOrThrow()

/**
 * Returns this integer as an encapsulated [StrictlyPositiveInt], or returns an
 * encapsulated [IllegalArgumentException] if this integer is
 * [negative][NegativeInt].
 */
internal fun AnyInt.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    toInt()
        .toStrictlyPositiveInt()

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

    override fun deserialize(value: Int): AnyInt = value.toAnyInt()
}
