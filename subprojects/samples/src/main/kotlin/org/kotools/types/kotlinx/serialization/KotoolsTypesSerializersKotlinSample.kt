package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress
import org.kotools.types.Zero

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object KotoolsTypesSerializersKotlinSample {
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        println(encoded) // 0
        val decoded: Zero = format.decodeFromString(encoded)
        println(zero == decoded) // true
    } // END

    fun emailAddress() {
        val format = Json {
            serializersModule = KotoolsTypesSerializers.emailAddress // TABS: 1
        }
        val emailAddress: EmailAddress =
            EmailAddress.fromString("contact@kotools.org") // TABS: 1
        val encoded: String = format.encodeToString(emailAddress)
        println(encoded) // "contact@kotools.org"
        val decoded: EmailAddress = format.decodeFromString(encoded)
        println(emailAddress == decoded) // true
    } // END

    fun zero() {
        val format = Json { serializersModule = KotoolsTypesSerializers.zero }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        println(encoded) // 0
        val decoded: Zero = format.decodeFromString(encoded)
        println(zero == decoded) // true
    } // END

    @Suppress("FunctionName")
    fun toString_override() {
        val message: String = KotoolsTypesSerializers.toString()
        println(message) // KotoolsTypesSerializers
    } // END
}
