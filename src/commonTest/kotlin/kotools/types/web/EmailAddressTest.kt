package kotools.types.web

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.deserializationErrorMessage
import kotools.types.internal.simpleNameOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private object Texts {
    val valid: Set<String> by lazy {
        setOf(
            "contact@kotools.org",
            "cont.act@kotools.org",
            "cont-act@kotools.org",
            "cont_act@kotools.org",
            "contact.123@kotools.org",
            "123contact@kotools.org",
            "contact123@kotools.org",
            "cont123act@kotools.org"
        )
    }
    val invalid: Set<String> by lazy {
        setOf(
            " contact@kotools.org",
            "contact-kotools.org",
            "contact@ko tools. org",
            "contact@kotools_org",
            "cont..act@kotools_org",
            "cont.-act@kotools_org",
            "cont._act@kotools_org",
            "cont-.act@kotools_org",
            "cont--act@kotools_org",
            "cont-_act@kotools_org",
            "cont_.act@kotools_org",
            "cont_-act@kotools_org",
            "cont__act@kotools_org"
        )
    }
}

@ExperimentalKotoolsTypesApi
@OptIn(InternalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun regex_should_pass() {
        val actual: Regex = EmailAddress.regex
        val expected = Regex("^\\S+@\\S+\\.\\S+\$")
        assertEquals(expected.pattern, actual.pattern)
    }

    @Test
    fun create_should_pass_with_a_valid_String(): Unit =
        Texts.valid.forEach { EmailAddress.create(it) }

    @Test
    fun create_should_fail_with_an_invalid_String(): Unit =
        Texts.invalid.forEach {
            val exception: IllegalArgumentException =
                assertFailsWith { EmailAddress.create(it) }
            val actual = ErrorMessage(exception)
            val expected: ErrorMessage = ErrorMessage.invalidEmailAddress(it)
            assertEquals(expected, actual)
        }

    @Test
    fun createOrNull_should_pass_with_a_valid_String(): Unit =
        Texts.valid.forEach {
            val actual: EmailAddress? = EmailAddress.createOrNull(it)
            assertNotNull(actual)
        }

    @Test
    fun createOrNull_should_fail_with_an_invalid_String(): Unit =
        Texts.invalid.forEach {
            val actual: EmailAddress? = EmailAddress.createOrNull(it)
            assertNull(actual)
        }
}

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun structural_equality_should_pass_with_the_same_object() {
        val text: String = Texts.valid.random()
        val first: EmailAddress = EmailAddress.create(text)
        val second: EmailAddress = first
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        val text: String = Texts.valid.random()
        val first: EmailAddress = EmailAddress.create(text)
        val second: EmailAddress = EmailAddress.create(text)
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val text: String = Texts.valid.random()
        val first: EmailAddress = EmailAddress.create(text)
        val second: Any? = null
        assertNotEquals(first, second)
    }

    @Test
    fun structural_equality_should_fail_with_another_object_that_is_not_an_EmailAddress() {
        val text: String = Texts.valid.random()
        val first: EmailAddress = EmailAddress.create(text)
        val second: Any = text
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        val text: String = Texts.valid.random()
        val first: EmailAddress = EmailAddress.create(text)
        val second: EmailAddress = EmailAddress.create("${text}x")
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun toString_should_pass() {
        val text: String = Texts.valid.random()
        val address: EmailAddress = EmailAddress.create(text)
        val actual: String = address.toString()
        assertEquals(expected = text, actual)
    }
}

@ExperimentalKotoolsTypesApi
@OptIn(InternalKotoolsTypesApi::class)
class EmailAddressSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serialName_should_be_the_qualified_name_of_EmailAddress() {
        val actual: String = serializer<EmailAddress>().descriptor.serialName
        val type: String = simpleNameOf<EmailAddress>()
        val expected = "${KotoolsTypesPackage.Web}.$type"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_STRING() {
        val actual: SerialKind = serializer<EmailAddress>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.STRING
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_a_String() {
        val text: String = Texts.valid.random()
        val address: EmailAddress = EmailAddress.create(text)
        val actual: String = Json.encodeToString(address)
        val expected: String = Json.encodeToString(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_from_a_valid_String(): Unit =
        Texts.valid.forEach {
            val encoded: String = Json.encodeToString(it)
            val actual: EmailAddress = Json.decodeFromString(encoded)
            val expected: EmailAddress = EmailAddress.create(it)
            assertEquals(expected, actual)
        }

    @Test
    fun deserialization_should_fail_from_an_invalid_String(): Unit =
        Texts.invalid.forEach {
            val exception: SerializationException = assertFailsWith {
                val encoded: String = Json.encodeToString(it)
                Json.decodeFromString<EmailAddress>(encoded)
            }
            val actual = ErrorMessage(exception)
            val expected: ErrorMessage =
                deserializationErrorMessage<EmailAddress>(it)
            assertEquals(expected, actual)
        }

    @Test
    fun serialization_processes_with_wrapped_EmailAddress_should_pass() {
        @Serializable
        data class Wrapper(val value: EmailAddress)

        val address: EmailAddress = EmailAddress.create("contact@kotools.org")
        val wrapper = Wrapper(address)
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}
