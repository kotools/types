package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.kotlinx.serialization.internal.EmailAddressAsStringSerializer
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class SerializersModuleTest {
    @Test
    fun includesEmailAddressAsStringSerializer() {
        // When
        val result: SerializersModule = KotoolsTypesSerializersModule()
        // Then
        val expected = EmailAddressAsStringSerializer()
        result.assertIncludes(expected)
    }

    @Test
    fun includesEmailAddressRegexAsStringSerializer() {
        val expected: KSerializer<EmailAddressRegex> =
            EmailAddressRegex.stringSerializer()
        KotoolsTypesSerializersModule()
            .assertIncludes(expected)
    }
}
