package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

@OptIn(ExperimentalKotoolsTypesApi::class)
class KotoolsTypesSerializersTest {
    @Test
    fun all_module_should_contain_all_serializers() {
        val module: SerializersModule = KotoolsTypesSerializers.all
        module.serializer<EmailAddress>()
        module.serializer<Zero>()
    }

    @Test
    fun emailAddress_should_contain_serializer_for_EmailAddress_type() {
        val module: SerializersModule = KotoolsTypesSerializers.emailAddress
        val serializer: KSerializer<EmailAddress> = module.serializer()
        assertSame(EmailAddressAsStringSerializer, serializer)
    }

    @Test
    fun zero_module_should_contain_serializer_for_Zero_type() {
        val module: SerializersModule = KotoolsTypesSerializers.zero
        val serializer: KSerializer<Zero> = module.serializer()
        assertSame(ZeroAsByteSerializer, serializer)
    }

    @Test
    fun toString_should_return_its_simple_name() {
        val actual: String = KotoolsTypesSerializers.toString()
        val expected: String = simpleNameOf<KotoolsTypesSerializers>()
        assertEquals(expected, actual)
    }
}
