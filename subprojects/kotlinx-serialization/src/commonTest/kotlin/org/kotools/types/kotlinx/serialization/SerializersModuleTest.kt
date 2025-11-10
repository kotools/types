package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.kotlinx.serialization.internal.EmailAddressAsStringSerializer
import org.kotools.types.kotlinx.serialization.internal.EmailAddressRegexAsStringSerializer
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
        // When
        val result: SerializersModule = KotoolsTypesSerializersModule()
        // Then
        val expected = EmailAddressRegexAsStringSerializer()
        result.assertIncludes(expected)
    }
}
