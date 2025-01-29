package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsStringSerializer()
    }

    @Test
    fun descriptor() {
        val serializer = ZeroAsStringSerializer()
        val descriptor: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "$serializer",
            PrimitiveKind.STRING
        )
        assertEquals(expected, descriptor)
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

    // ----------------------- Serialization operations ------------------------

    @Test
    fun serialize() {
        val serializer = ZeroAsStringSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        val expected = "\"$zero\""
        assertEquals(expected, encoded)
    }

    @Test
    fun deserialize() {
        val deserializer = ZeroAsStringSerializer()
        val expected = Zero()
        sequenceOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map { Json.decodeFromString(deserializer, "\"$it\"") }
            .forEach { assertEquals(expected, it) }
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
