package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressAsStringSerializerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddressAsStringSerializer() {
        val serializer = EmailAddressAsStringSerializer()
        val other: String = serializer.toString()
        val actual: Boolean = serializer.equals(other)
        val message = "Instance of '$serializer' shouldn't equal another type."
        assertFalse(actual, message)
    }

    @Test
    fun hashCodeShouldUseToStringMethod() {
        val serializer = EmailAddressAsStringSerializer()
        val actual: Int = serializer.hashCode()
        val expected: Int = hashCodeOf("$serializer")
        val message = "Hash code of '$serializer' should be calculated from " +
                "its string representation."
        assertEquals(expected, actual, message)
    }
}
