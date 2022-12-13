package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal sealed class Serializer<T, D>(
    private val delegate: KSerializer<D>,
    private val toDelegatedType: (T) -> D,
    private val toType: (D) -> Result<T>
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: T) {
        val b: D = toDelegatedType(value)
        delegate.serialize(encoder, b)
    }

    override fun deserialize(decoder: Decoder): T = delegate
        .deserialize(decoder)
        .let(toType)
        .getOrThrow()
}
