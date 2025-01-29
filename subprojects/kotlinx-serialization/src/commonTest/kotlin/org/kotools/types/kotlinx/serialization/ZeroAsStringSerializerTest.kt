package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.json.Json
import kotools.types.internal.hashCodeOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ErrorMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsStringSerializerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldFailWithAnotherTypeThanZeroAsStringSerializer() {
        val serializer = ZeroAsStringSerializer()
        val other: Any = "Oops"
        val actual: Boolean = serializer == other
        val message = "Instance of '$serializer' shouldn't equal another type."
        assertFalse(actual, message)
    }

    @Test
    fun hashCodeShouldUseToStringMethod() {
        val serializer = ZeroAsStringSerializer()
        val actual: Int = serializer.hashCode()
        val expected: Int = hashCodeOf("$serializer")
        val message = "Hash code of '$serializer' should be calculated from " +
                "its string representation."
        assertEquals(expected, actual, message)
    }

    // ----------------------- Serialization operations ------------------------

    @Test
    fun deserializeShouldFailWithStringNotRepresentingZero() {
        val deserializer = ZeroAsStringSerializer()
        listOf("", " ", ".", "0.", ".0", "abc").forEach {
            val exception: IllegalArgumentException = assertFailsWith {
                Json.decodeFromString(deserializer, "\"$it\"")
            }
            val actual: String? = exception.message
            val expected: String = ErrorMessage.invalidZero(it)
            assertEquals(expected, actual)
        }
    }
}
