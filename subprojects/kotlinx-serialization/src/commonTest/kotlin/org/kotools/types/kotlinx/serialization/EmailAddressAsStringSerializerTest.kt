package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressAsStringSerializerTest {
    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddressAsStringSerializer() {
        val serializer = EmailAddressAsStringSerializer()
        val other: String = serializer.toString()
        val actual: Boolean = serializer.equals(other)
        val message = "Instance of '$serializer' shouldn't equal another type."
        assertFalse(actual, message)
    }
}
