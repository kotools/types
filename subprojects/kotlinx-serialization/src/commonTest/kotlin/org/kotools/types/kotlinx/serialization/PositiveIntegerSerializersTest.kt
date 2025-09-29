package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class PositiveIntegerSerializersTest {
    // -------------------------- stringSerializer() ---------------------------

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun serializationAsStringPasses() {
        val serializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val text: String = (1..Int.MAX_VALUE).random()
            .toString()
        val integer: PositiveInteger = PositiveInteger.of(text) ?: fail()
        val actual: String = Json.encodeToString(serializer, integer)
        val expected: String = Json.encodeToString(text)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializationPassesFromStringIntegerGreaterThanZero() {
        val text: String = (1..Int.MAX_VALUE).random()
            .toString()
        val encoded: String = Json.encodeToString(text)
        val deserializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val actual: PositiveInteger =
            Json.decodeFromString(deserializer, encoded)
        val expected: PositiveInteger = PositiveInteger.of(text) ?: fail()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializationPassesFromStringSignedIntegerGreaterThanZero() {
        val text: String = (1..Int.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
        val encoded: String = Json.encodeToString(text)
        val deserializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val actual: PositiveInteger =
            Json.decodeFromString(deserializer, encoded)
        val expected: PositiveInteger = PositiveInteger.of(text) ?: fail()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializationFailsFromStringOtherThanInteger() {
        val text = "oops"
        val encoded: String = Json.encodeToString(text)
        val deserializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(deserializer, encoded)
        }
        val actual: String? = exception.message
        val expected = "'$text' doesn't represent an integer greater than zero."
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializationFailsFromStringIntegerRepresentingZero() {
        val text = "0"
        val encoded: String = Json.encodeToString(text)
        val deserializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(deserializer, encoded)
        }
        val actual: String? = exception.message
        val expected = "'$text' doesn't represent an integer greater than zero."
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializationFailsFromStringIntegerLessThanZero() {
        val text: String = (Int.MIN_VALUE..-1).random()
            .toString()
        val encoded: String = Json.encodeToString(text)
        val deserializer: KSerializer<PositiveInteger> =
            PositiveInteger.stringSerializer()
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(deserializer, encoded)
        }
        val actual: String? = exception.message
        val expected = "'$text' doesn't represent an integer greater than zero."
        assertEquals(expected, actual)
    }
}
