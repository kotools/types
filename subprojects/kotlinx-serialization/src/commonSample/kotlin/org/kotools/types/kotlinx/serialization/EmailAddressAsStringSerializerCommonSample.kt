package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class EmailAddressAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        EmailAddressAsStringSerializer()
    }

    @Test
    fun toStringOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
