package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsByteSerializerTest {
    @Test
    fun equalsShouldFailWithAnotherTypeThanZeroAsByteSerializer() {
        val serializer = ZeroAsByteSerializer()
        val other: Any = "Oops"
        val actual: Boolean = serializer == other
        assertFalse(actual)
    }
}
