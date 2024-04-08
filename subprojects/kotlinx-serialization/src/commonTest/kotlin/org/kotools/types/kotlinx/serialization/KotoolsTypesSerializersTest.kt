package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KotoolsTypesSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun zero_module_should_contain_serializer_for_Zero_type() {
        val module: SerializersModule = KotoolsTypesSerializers.zero
        module.serializer<Zero>()
    }

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
