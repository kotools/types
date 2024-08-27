package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class ZeroAsByteSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsByteSerializer()
    }

    @Test
    fun descriptor() {
        val serializer = ZeroAsByteSerializer()
        val descriptor: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "$serializer",
            PrimitiveKind.BYTE
        )
        assertEquals(expected, descriptor)
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val serializer = ZeroAsByteSerializer()
        val other = ZeroAsByteSerializer()
        val equality: Boolean = serializer == other
        val message = "Instances of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    @Test
    fun hashCodeOverride() {
        val serializer = ZeroAsByteSerializer()
        val other = ZeroAsByteSerializer()
        val equality: Boolean = serializer.hashCode() == other.hashCode()
        assertTrue(equality)
    }

    // ----------------------- Serialization operations ------------------------

    @Test
    fun serialize() {
        val serializer = ZeroAsByteSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        val expected: String = zero.toByte()
            .let(Json.Default::encodeToString)
        assertEquals(expected, encoded)
    }

    @Test
    fun deserialize() {
        val deserializer = ZeroAsByteSerializer()
        val zero = Zero()
        val decoded: Zero = zero.toByte()
            .let(Json.Default::encodeToString)
            .let { Json.decodeFromString(deserializer, string = it) }
        assertEquals(expected = zero, decoded)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val serializer = ZeroAsByteSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
