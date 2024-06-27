package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class KotoolsTypesSerializersCommonSample {
    @Test
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        assertEquals(expected = "0", actual = encoded)
        val decoded: Zero = format.decodeFromString(encoded)
        assertEquals(expected = zero, actual = decoded)
    }

    @Test
    fun emailAddress() {
        val format = Json {
            serializersModule = KotoolsTypesSerializers.emailAddress
        }
        val emailAddress: EmailAddress =
            EmailAddress.fromString("contact@kotools.org")
        val encoded: String = format.encodeToString(emailAddress)
        assertEquals(expected = "\"contact@kotools.org\"", actual = encoded)
        val decoded: EmailAddress = format.decodeFromString(encoded)
        assertEquals(expected = emailAddress, actual = decoded)
    }

    @Test
    fun zero() {
        val format = Json { serializersModule = KotoolsTypesSerializers.zero }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        assertEquals(expected = "0", actual = encoded)
        val decoded: Zero = format.decodeFromString(encoded)
        assertEquals(expected = zero, actual = decoded)
    }

    @Test
    fun toStringOverride() {
        val actual =
            "$KotoolsTypesSerializers" // or KotoolsTypesSerializers.toString()
        val expected = "KotoolsTypesSerializers"
        assertEquals(expected, actual)
    }
}
