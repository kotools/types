package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import org.kotools.types.internal.ErrorDeprecated

internal class DeserializationErrorDeprecated(
    private val deserializer: DeserializationStrategy<*>,
    private val decodedValue: Any,
    private val reason: ErrorDeprecated? = null
) : ErrorDeprecated {
    @OptIn(ExperimentalSerializationApi::class)
    override val message: String
        get() {
            val formattedSerialName: String =
                super.format(this.deserializer.descriptor.serialName)
            val formattedDecodedValue: String = super.format(this.decodedValue)
            val errorMessage = "Unable to deserialize $formattedSerialName " +
                    "from $formattedDecodedValue."
            return this.reason?.let { "$errorMessage ${it.message}" }
                ?: errorMessage
        }
}
