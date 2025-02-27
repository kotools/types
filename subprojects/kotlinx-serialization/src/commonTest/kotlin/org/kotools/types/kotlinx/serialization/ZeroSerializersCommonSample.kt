package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroSerializersCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun byteSerializer() {
        val serializer: KSerializer<Zero> = Zero.byteSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals("$zero", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }
}
