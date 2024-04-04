@file:Suppress("DEPRECATION")

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
class EmailAddressCompanionTest {
    @Test
    fun regex_should_pass() {
        val actual: Regex = EmailAddress.regex
        val expected = Regex("^\\S+@\\S+\\.\\S+\$")
        assertEquals(expected.pattern, actual.pattern)
    }
}

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun constructor_should_pass_with_a_valid_String() {
        EmailAddress(value = "contact@kotools.org")
    }

    @OptIn(InternalKotoolsTypesApi::class)
    private fun testConstructorFailure(value: String) {
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress(value)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = ErrorMessage.invalidEmailAddress(value)
        assertEquals(expected, actual)
    }

    @Test
    fun constructor_should_fail_with_a_String_missing_an_at_sign(): Unit =
        testConstructorFailure("contactKotools.org")

    @Test
    fun constructor_should_fail_with_a_String_missing_a_dot_domain(): Unit =
        testConstructorFailure("contact@kotoolsOrg")

    @Test
    fun constructor_should_fail_with_a_String_having_a_whitespace_in_local_part(): Unit =
        testConstructorFailure(" con  tact @kotools.org")

    @Test
    fun constructor_should_fail_with_a_String_having_a_whitespace_in_first_label_of_domain(): Unit =
        testConstructorFailure("contact@ ko tools .org")

    @Test
    fun constructor_should_fail_with_a_String_having_a_whitespace_in_second_label_of_domain() {
        testConstructorFailure("contact@kotools. or g ")
    }

    @Test
    fun structural_equality_should_pass_with_the_same_object() {
        val text: String = Texts.valid.random()
        val first = EmailAddress(text)
        val second: EmailAddress = first
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        val text: String = Texts.valid.random()
        val first = EmailAddress(text)
        val second = EmailAddress(text)
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val text: String = Texts.valid.random()
        val first = EmailAddress(text)
        val second: Any? = null
        assertNotEquals(first, second)
    }

    @Test
    fun structural_equality_should_fail_with_another_object_that_is_not_an_EmailAddress() {
        val text: String = Texts.valid.random()
        val first = EmailAddress(text)
        val second: Any = text
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        val text: String = Texts.valid.random()
        val first = EmailAddress(text)
        val second = EmailAddress("${text}x")
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun toString_should_pass() {
        val text: String = Texts.valid.random()
        val address = EmailAddress(text)
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
        val address = EmailAddress(text)
        val actual: String = Json.encodeToString(address)
        val expected: String = Json.encodeToString(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_from_a_valid_String(): Unit =
        Texts.valid.forEach {
            val encoded: String = Json.encodeToString(it)
            val actual: EmailAddress = Json.decodeFromString(encoded)
            val expected = EmailAddress(it)
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

        val address = EmailAddress("contact@kotools.org")
        val wrapper = Wrapper(address)
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}
