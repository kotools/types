package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import org.kotools.types.internal.Error

internal class DeserializationError(
    private val deserializer: DeserializationStrategy<*>,
    private val decodedValue: Any,
    private val reason: String
) {
    @OptIn(ExperimentalSerializationApi::class)
    override fun toString(): String {
        val serialName: String = this.deserializer.descriptor.serialName
        val message =
            "Unable to deserialize '$serialName' from '${this.decodedValue}'."
        val error = Error(message, this.reason)
        return error.toString()
    }
}
