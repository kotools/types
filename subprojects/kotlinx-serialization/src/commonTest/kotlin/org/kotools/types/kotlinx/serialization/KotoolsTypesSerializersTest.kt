package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class KotoolsTypesSerializersTest {
    @Test
    fun all_module_should_contain_all_serializers() {
        val module: SerializersModule = KotoolsTypesSerializers.all
        module.serializer<Zero>()
    }

    @Test
    fun zero_module_should_contain_serializer_for_Zero_type() {
        val module: SerializersModule = KotoolsTypesSerializers.zero
        module.serializer<Zero>()
    }

    @Test
    fun toString_should_return_its_simple_name() {
        val actual: String = KotoolsTypesSerializers.toString()
        val expected: String = simpleNameOf<KotoolsTypesSerializers>()
        assertEquals(expected, actual)
    }
}
