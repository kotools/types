package kotools.types.internal

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.kotools.types.internal.InternalKotoolsTypesApi

/**
 * Creates a serializer with the specified [deserializationStrategy] and
 * [intConverter] for serializing the type [T] as [Int].
 */
@InternalKotoolsTypesApi
public fun <T : Any> intSerializer(
    deserializationStrategy: DeserializationStrategy<T>,
    intConverter: (T) -> Int
): KSerializer<T> = object : KSerializer<T> {
    override val descriptor: SerialDescriptor by lazy {
        deserializationStrategy.descriptor
    }

    override fun serialize(encoder: Encoder, value: T) {
        val intValue: Int = intConverter(value)
        encoder.encodeInt(intValue)
    }

    override fun deserialize(decoder: Decoder): T =
        deserializationStrategy.deserialize(decoder)
}

/**
 * Creates a serializer with the specified [deserializationStrategy] for
 * serializing the type [T] as [String].
 */
@InternalKotoolsTypesApi
public fun <T : Any> stringSerializer(
    deserializationStrategy: DeserializationStrategy<T>
): KSerializer<T> = object : KSerializer<T> {
    override val descriptor: SerialDescriptor by lazy {
        deserializationStrategy.descriptor
    }

    override fun serialize(encoder: Encoder, value: T): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): T =
        deserializationStrategy.deserialize(decoder)
}
