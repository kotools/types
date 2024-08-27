package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class EmailAddressAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        EmailAddressAsStringSerializer()
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val other = EmailAddressAsStringSerializer()
        val equality: Boolean = serializer == other
        val message = "Instances of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    @Test
    fun hashCodeOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val other = EmailAddressAsStringSerializer()
        val equality: Boolean = serializer.hashCode() == other.hashCode()
        val message = "Hash codes of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
