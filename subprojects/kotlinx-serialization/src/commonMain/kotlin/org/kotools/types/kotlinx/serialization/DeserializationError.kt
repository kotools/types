package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import org.kotools.types.internal.Error

internal class DeserializationError(
    private val deserializer: DeserializationStrategy<*>,
    private val decodedValue: Any,
    private val reason: Error? = null
) : Error {
    @OptIn(ExperimentalSerializationApi::class)
    override val message: String
        get() {
            val serialName: String = this.deserializer.descriptor.serialName
            val errorMessage = "Unable to deserialize \"$serialName\" from " +
                    "\"${decodedValue}\"."
            return this.reason?.let { "$errorMessage ${it.message}" }
                ?: errorMessage
        }
}
