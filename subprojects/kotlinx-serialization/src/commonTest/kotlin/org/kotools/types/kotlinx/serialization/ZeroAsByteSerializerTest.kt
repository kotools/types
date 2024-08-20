package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
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
        assertFalse(actual)
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
