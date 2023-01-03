package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.shouldHaveAMessage
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import kotlin.test.Test
import kotlin.test.assertFailsWith

class StrictlyNegativeIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.min
        result.asInt shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_minus_one() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.max
        result.asInt shouldEqual -1
    }

    @Test
    fun random_should_return_different_values() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.random()
        result.asInt shouldNotEqual StrictlyNegativeInt.random()
    }
}

class StrictlyNegativeIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        "$x" shouldEqual "${x.asInt}"
    }

    @Test
    fun int_asStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random().asInt
        val result: Result<StrictlyNegativeInt> = value.asStrictlyNegativeInt
        result.getOrThrow().asInt shouldEqual value
    }

    @Test
    fun int_asStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> =
            PositiveInt.random().asInt.asStrictlyNegativeInt
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }
}

class StrictlyNegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyNegativeInt_as_serial_name(): Unit =
        StrictlyNegativeInt.serializer()
            .descriptor
            .serialName shouldEqual "${Package.number}.StrictlyNegativeInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: String = Json.encodeToString(x)
        result shouldEqual Json.encodeToString(x.asInt)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random().asInt
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        result.asInt shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = PositiveInt.random().asInt
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        exception.shouldHaveAMessage()
    }
}
