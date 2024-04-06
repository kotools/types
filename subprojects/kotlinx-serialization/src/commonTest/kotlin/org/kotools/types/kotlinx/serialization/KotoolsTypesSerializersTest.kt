package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KotoolsTypesSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toString_should_return_its_simple_name() {
        val actual: String = KotoolsTypesSerializers.toString()
        val type: KClass<KotoolsTypesSerializers> =
            KotoolsTypesSerializers::class
        val expected: String = assertNotNull(
            actual = type.simpleName,
            message = "Getting simple name of '$type' should pass."
        )
        assertEquals(expected, actual)
    }
}
