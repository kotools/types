package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

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

// -------------------------------- Assertions ---------------------------------

private inline fun <reified T> SerializersModule.assertIncludes(
    expected: KSerializer<T>
) {
    val actual: KSerializer<T> = this.serializer<T>()
    val message = "Serializers module includes '$expected'."
    assertEquals(expected, actual, message)
}
