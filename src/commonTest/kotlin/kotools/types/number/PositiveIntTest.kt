package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.shouldHaveAMessage
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import kotlin.test.Test
import kotlin.test.assertFailsWith

class PositiveIntCompanionTest {
    @Test
    fun min_should_equal_zero() {
        val result: ZeroInt = PositiveInt.min
        result shouldEqual ZeroInt
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = PositiveInt.max
        result.toInt() shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val result: PositiveInt = PositiveInt.random()
        result.toInt() shouldNotEqual PositiveInt.random().toInt()
    }
}

class PositiveIntTest {
    @Test
    fun int_asPositiveInt_should_pass_with_a_positive_Int() {
        val expected: Int = PositiveInt.random().toInt()
        val result: Int = expected.asPositiveInt.getOrThrow().toInt()
        result shouldEqual expected
    }

    @Test
    fun int_asPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> =
            StrictlyNegativeInt.random().toInt().asPositiveInt
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }
}

class PositiveIntSerializerTest {
    private val serializer: KSerializer<PositiveInt> = PositiveIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_PositiveInt_as_serial_name(): Unit =
        serializer.descriptor
            .serialName shouldEqual "${Package.number}.PositiveInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: PositiveInt = PositiveInt.random()
        val result: String = Json.encodeToString(serializer, x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val expected: Int = PositiveInt.random().toInt()
        val encoded: String = Json.encodeToString(expected)
        val result: PositiveInt =
            Json.decodeFromString(PositiveIntSerializer, encoded)
        result.toInt() shouldEqual expected
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(PositiveIntSerializer, encoded)
        }
        exception.shouldHaveAMessage()
    }
}
