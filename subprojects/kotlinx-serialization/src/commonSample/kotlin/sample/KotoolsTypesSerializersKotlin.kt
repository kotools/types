package sample

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress
import org.kotools.types.Zero
import org.kotools.types.kotlinx.serialization.KotoolsTypesSerializers
import kotlin.test.Test

class KotoolsTypesSerializersKotlin {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun all() {
        val format = Json { serializersModule = KotoolsTypesSerializers.all }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        check(encoded == "0")
        val decoded: Zero = format.decodeFromString(encoded)
        check(decoded == zero)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun emailAddress() {
        val format = Json {
            serializersModule = KotoolsTypesSerializers.emailAddress
        }
        val emailAddress: EmailAddress =
            EmailAddress.fromString("contact@kotools.org")
        val encoded: String = format.encodeToString(emailAddress)
        check(encoded == "\"contact@kotools.org\"")
        val decoded: EmailAddress = format.decodeFromString(encoded)
        check(decoded == emailAddress)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun zero() {
        val format = Json { serializersModule = KotoolsTypesSerializers.zero }
        val zero = Zero()
        val encoded: String = format.encodeToString(zero)
        check(encoded == "0")
        val decoded: Zero = format.decodeFromString(encoded)
        check(decoded == zero)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toStringOverride() {
        val result: String = KotoolsTypesSerializers.toString()
        check(result == "KotoolsTypesSerializers")
    }
}
