package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class ZeroAsByteSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsByteSerializer()
    }

    @Test
    fun equalsOverride() {
        val serializer = ZeroAsByteSerializer()
        val other = ZeroAsByteSerializer()
        val equality: Boolean = serializer == other
        assertTrue(equality)
    }

    @Test
    fun hashCodeOverride() {
        val serializer = ZeroAsByteSerializer()
        val other = ZeroAsByteSerializer()
        val equality: Boolean = serializer.hashCode() == other.hashCode()
        assertTrue(equality)
    }

    @Test
    fun toStringOverride() {
        val serializerAsString: String = ZeroAsByteSerializer()
            .toString()
        val expected: String =
            checkNotNull(ZeroAsByteSerializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
