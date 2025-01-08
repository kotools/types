package org.kotools.types.kotlinx.serialization

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsStringSerializer()
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val serializer = ZeroAsStringSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
