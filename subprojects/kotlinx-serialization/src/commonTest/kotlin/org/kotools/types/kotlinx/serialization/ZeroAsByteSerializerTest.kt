package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsByteSerializerTest {
    @Test
    fun constructorShouldPass() {
        ZeroAsByteSerializer()
    }

    @Test
    fun equalsShouldPassWithZeroAsByteSerializer() {
        val serializer = ZeroAsByteSerializer()
        val other = ZeroAsByteSerializer()
        val actual: Boolean = serializer == other
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanZeroAsByteSerializer() {
        val serializer = ZeroAsByteSerializer()
        val other: Any = "Oops"
        val actual: Boolean = serializer == other
        assertFalse(actual)
    }

    @Test
    fun toStringShouldPass() {
        val serializer = ZeroAsByteSerializer()
        val actual: String = serializer.toString()
        val expected: String = simpleNameOf(serializer::class)
        assertEquals(expected, actual)
    }
}
