package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class KotoolsTypesSerializersCommonSample {
    @Test
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        assertEquals(expected = "0", actual = encoded)
        val decoded: Zero = format.decodeFromString(encoded)
        assertEquals(expected = zero, actual = decoded)
    }

    @Test
    fun toStringOverride() {
        val actual =
            "$KotoolsTypesSerializers" // or KotoolsTypesSerializers.toString()
        val expected = "KotoolsTypesSerializers"
        assertEquals(expected, actual)
    }
}
