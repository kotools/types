package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroSerializersSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun byteSerializer() {
        val serializer: KSerializer<Zero> = Zero.byteSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals("$zero", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun shortSerializer() {
        val serializer: KSerializer<Zero> = Zero.shortSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals("$zero", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun intSerializer() {
        val serializer: KSerializer<Zero> = Zero.intSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals("$zero", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun longSerializer() {
        val serializer: KSerializer<Zero> = Zero.longSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals("$zero", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun floatSerializer() {
        val serializer: KSerializer<Zero> = Zero.floatSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        val expectedEncoding: String = zero.toFloat()
            .toString()
        assertEquals(expectedEncoding, encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun doubleSerializer() {
        val serializer: KSerializer<Zero> = Zero.doubleSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        val expectedEncoding: String = zero.toDouble()
            .toString()
        assertEquals(expectedEncoding, encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<Zero> = Zero.stringSerializer()
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals(expected = "\"$zero\"", encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(zero, decoded)
    }
}
