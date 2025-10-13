package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExceptionMessage

@ExperimentalKotoolsTypesApi
internal class EmailAddressAsStringSerializer : StringSerializer<EmailAddress> {
    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is EmailAddressAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun deserialize(text: String): EmailAddress =
        requireNotNull(EmailAddress of text) {
            ExceptionMessage.invalidEmailAddress(text)
        }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
