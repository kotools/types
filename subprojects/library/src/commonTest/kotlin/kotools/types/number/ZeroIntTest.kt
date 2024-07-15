@file:OptIn(InternalKotoolsTypesApi::class)

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.simpleNameOf
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ZeroIntTest {
    @Test
    fun toInt_should_return_the_zero_integer(): Unit =
        ZeroInt.toInt() shouldEqual 0

    @Test
    fun toString_should_behave_like_the_zero_integer(): Unit =
        "$ZeroInt" shouldEqual "0"
}

class ZeroIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_ZeroInt() {
        val actual: String = serializer<ZeroInt>().descriptor.serialName
        val simpleName: String = simpleNameOf<ZeroInt>()
        val expected = "${KotoolsTypesPackage.Number}.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<ZeroInt>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialize_should_behave_like_the_zero_integer() {
        val actual: String = Json.encodeToString(ZeroInt)
        val expected: String = Json.encodeToString(0)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialize_should_pass_with_the_zero_integer() {
        val actual: ZeroInt = Json.decodeFromString("0")
        assertEquals(expected = ZeroInt, actual)
    }

    @Test
    fun deserialize_should_fail_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<ZeroInt>(encoded) }
        exception.shouldHaveAMessage()
    }

    @Test
    fun serialization_processes_of_wrapped_ZeroInt_should_pass() {
        @Serializable
        data class Wrapper(val value: ZeroInt = ZeroInt)

        val wrapper = Wrapper()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}
