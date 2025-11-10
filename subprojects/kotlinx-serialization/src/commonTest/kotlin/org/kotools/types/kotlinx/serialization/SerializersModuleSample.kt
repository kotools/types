package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

class SerializersModuleSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun emailAddressAsString() {
        val format = Json {
            this.serializersModule = KotoolsTypesSerializersModule()
        }
        val emailAddress: EmailAddress? = EmailAddress.of("contact@kotools.org")
        checkNotNull(emailAddress)
        val encoded: String = format.encodeToString(emailAddress)
        check(encoded == "\"contact@kotools.org\"")
        val decoded: EmailAddress = format.decodeFromString(encoded)
        check(decoded == emailAddress)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun emailAddressRegexAsString() {
        val format = Json {
            this.serializersModule = KotoolsTypesSerializersModule()
        }
        val regex: EmailAddressRegex = EmailAddressRegex.default() // ^\S+@\S+\.\S+$
        val encoded: String = format.encodeToString(regex)
        check(encoded == """"^\\S+@\\S+\\.\\S+$"""")
        val decoded: EmailAddressRegex = format.decodeFromString(encoded)
        check(decoded == regex)
    }
}
