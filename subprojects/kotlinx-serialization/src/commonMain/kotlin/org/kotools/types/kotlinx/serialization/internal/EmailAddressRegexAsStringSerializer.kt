package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi

@ExperimentalKotoolsTypesApi
internal class EmailAddressRegexAsStringSerializer :
    StringSerializer<EmailAddressRegex> {
    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is EmailAddressRegexAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")


    // ----------------------- Serialization operations ------------------------

    override fun deserialize(text: String): EmailAddressRegex =
        EmailAddressRegex.orThrow(text)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
