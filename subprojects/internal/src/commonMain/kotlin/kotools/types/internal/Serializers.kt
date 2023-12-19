/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Creates a serializer with the specified [deserializationStrategy] and
 * [intConverter] for serializing the type [T] as [Int].
 */
public fun <T> intSerializer(
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
