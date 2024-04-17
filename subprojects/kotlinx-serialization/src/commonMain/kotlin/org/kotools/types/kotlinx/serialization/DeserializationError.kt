package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException

internal class DeserializationError(
    private val deserializer: DeserializationStrategy<*>,
    private val decodedValue: Any
) {
    @OptIn(ExperimentalSerializationApi::class)
    val message: String
        get() {
            val serialName: String = this.deserializer.descriptor.serialName
            return "Unable to deserialize '$serialName' from ${decodedValue}."
        }

    fun fail(): Nothing = throw SerializationException(message)
}
