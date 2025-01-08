package org.kotools.types.kotlinx.serialization

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsStringSerializer()
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val serializer = ZeroAsStringSerializer()
        val other = ZeroAsStringSerializer()
        val equality: Boolean = serializer == other
        val message = "Instances of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    @Test
    fun hashCodeOverride() {
        val serializer = ZeroAsStringSerializer()
        val other = ZeroAsStringSerializer()
        val equality: Boolean = serializer.hashCode() == other.hashCode()
        val message = "Hash codes of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val serializer = ZeroAsStringSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
