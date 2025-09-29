package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
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
    fun includesPositiveIntegerAsStringSerializer() {
        val expected: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        KotoolsTypesSerializersModule()
            .assertIncludes(expected)
    }
}
