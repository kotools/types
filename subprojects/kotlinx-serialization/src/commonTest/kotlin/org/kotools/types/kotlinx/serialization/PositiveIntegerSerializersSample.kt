package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PositiveIntegerSerializersSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val encoded: String = Json.encodeToString(serializer, integer)
        assertEquals(expected = "\"$integer\"", encoded)
        val decoded: PositiveInteger =
            Json.decodeFromString(serializer, encoded)
        assertEquals(expected = integer, decoded)
    }
}
