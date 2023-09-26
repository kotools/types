package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

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
    fun descriptor_should_have_the_qualified_name_of_ZeroInt_as_serial_name() {
        val actual: String = ZeroInt.serializer().descriptor.serialName
        val expected: String = assertNotNull(ZeroInt::class.qualifiedName)
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
        val expected = ZeroInt
        assertEquals(expected, actual)
    }

    @Test
    fun deserialize_should_fail_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<ZeroInt>(encoded) }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String =
            ZeroIntSerializer.deserializationErrorMessage(value)
        assertEquals(expectedMessage, actualMessage)
    }
}
