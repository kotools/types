package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class SerializersModuleTest {
    @Test
    fun includesEmailAddressAsStringSerializer() {
        val expected: KSerializer<EmailAddress> =
            EmailAddress.stringSerializer()
        KotoolsTypesSerializersModule()
            .assertIncludes(expected)
    }

    @Test
    fun includesEmailAddressRegexAsStringSerializer() {
        val expected: KSerializer<EmailAddressRegex> =
            EmailAddressRegex.stringSerializer()
        KotoolsTypesSerializersModule()
            .assertIncludes(expected)
    }

    @Test
    fun includesZeroAsIntSerializer() {
        val expected: KSerializer<Zero> = Zero.intSerializer()
        KotoolsTypesSerializersModule()
            .assertIncludes(expected)
    }
}
