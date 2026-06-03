package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
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
        @Serializable
        data class User(@Contextual val email: EmailAddress)

        val email: EmailAddress? = EmailAddress.of("local@domain.org")
        checkNotNull(email)
        val user = User(email)

        val format = Json {
            this.serializersModule = KotoolsTypesSerializersModule()
        }

        val encoded: String = format.encodeToString(user)
        check(encoded == """{"email":"local@domain.org"}""")

        val decoded: User = format.decodeFromString(encoded)
        check(decoded == user)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun emailAddressRegexAsString() {
        @Serializable
        data class CustomEmailAddress(
            @Contextual val email: EmailAddress,
            @Contextual val regex: EmailAddressRegex
        )

        val email: EmailAddress? = EmailAddress.of("local@domain.org")
        checkNotNull(email)
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val customEmail = CustomEmailAddress(email, regex)

        val format = Json {
            this.serializersModule = KotoolsTypesSerializersModule()
        }

        val encoded: String = format.encodeToString(customEmail)
        val encodedEmail = """"email":"local@domain.org""""
        val encodedRegex = """"regex":"^[a-z]+@[a-z]+\\.[a-z]+$""""
        check(encoded == """{$encodedEmail,$encodedRegex}""")

        val decoded: CustomEmailAddress = format.decodeFromString(encoded)
        check(decoded == customEmail)
    }
}
