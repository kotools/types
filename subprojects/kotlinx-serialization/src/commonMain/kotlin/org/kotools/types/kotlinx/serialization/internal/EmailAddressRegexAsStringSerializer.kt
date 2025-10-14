package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExceptionMessage

@ExperimentalKotoolsTypesApi
internal class EmailAddressRegexAsStringSerializer :
    StringSerializer<EmailAddressRegex> {
    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is EmailAddressRegexAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")


    // ----------------------- Serialization operations ------------------------

    override fun deserialize(text: String): EmailAddressRegex =
        requireNotNull(EmailAddressRegex of text) {
            ExceptionMessage.invalidEmailAddressPattern(text)
        }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
