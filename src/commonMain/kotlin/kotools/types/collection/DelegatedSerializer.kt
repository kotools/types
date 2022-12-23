package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.text.NotBlankString

internal sealed interface DelegatedSerializer<A, B : A> : KSerializer<B> {
    val delegate: KSerializer<A>
    val serialName: Result<NotBlankString>
    val deserializationException: IllegalArgumentException

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor
        get() = serialName
            .map { SerialDescriptor(it.value, delegate.descriptor) }
            .getOrThrow()

    override fun serialize(encoder: Encoder, value: B): Unit =
        encoder.encodeSerializableValue(delegate, value)

    override fun deserialize(decoder: Decoder): B = decoder
        .decodeSerializableValue(delegate)
        .toResultOfB()
        .getOrNull()
        ?: throw SerializationException(deserializationException)

    fun A.toResultOfB(): Result<B>
}
