package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.internal.hashCodeOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.InvalidZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsByteSerializerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldFailWithAnotherTypeThanZeroAsByteSerializer() {
        val serializer = ZeroAsByteSerializer()
        val other: Any = "Oops"
        val actual: Boolean = serializer == other
        val message = "Instance of '$serializer' shouldn't equal another type."
        assertFalse(actual, message)
    }

    @Test
    fun hashCodeShouldUseToStringMethod() {
        val serializer = ZeroAsByteSerializer()
        val actual: Int = serializer.hashCode()
        val expected: Int = hashCodeOf("$serializer")
        val message = "Hash code of '$serializer' should be calculated from " +
                "its string representation."
        assertEquals(expected, actual, message)
    }

    // ----------------------- Serialization operations ------------------------

    @Test
    fun deserializeShouldFailWithByteOtherThanZero() {
        val deserializer = ZeroAsByteSerializer()
        val number: Byte = setOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val encoded: String = Json.encodeToString(number)
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(deserializer, string = encoded)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }
}
