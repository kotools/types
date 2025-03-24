package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class KotoolsTypesSerializersSample {
    @Test
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        assertEquals("0", encoded)
        val decoded: Zero = format.decodeFromString(encoded)
        assertEquals(zero, decoded)
    }

    @Test
    fun toStringOverride() {
        val result =
            "$KotoolsTypesSerializers" // or KotoolsTypesSerializers.toString()
        assertEquals("KotoolsTypesSerializers", result)
    }
}
