package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class PositiveIntegerSerializersSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val integer: PositiveInteger = (1..Int.MAX_VALUE).random()
            .let(PositiveInteger.Companion::orThrow)
        val encoded: String = Json.encodeToString(serializer, integer)
        assertEquals("\"$integer\"", encoded)
        val decoded: PositiveInteger =
            Json.decodeFromString(serializer, encoded)
        assertEquals(integer, decoded)
    }
}
