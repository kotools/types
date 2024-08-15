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
        val first = ZeroAsByteSerializer()
        val second = ZeroAsByteSerializer()
        val equality: Boolean = first == second // or first.equals(second)
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
