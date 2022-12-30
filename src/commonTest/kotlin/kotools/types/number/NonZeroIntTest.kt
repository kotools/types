package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NonZeroIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = NonZeroInt.min
        result.asInt shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = NonZeroInt.max
        result.asInt shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val x: NonZeroInt = NonZeroInt.random()
        x.asInt shouldNotEqual NonZeroInt.random().asInt
    }
}

class NonZeroIntTest {
    @Test
    fun int_asNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random().asInt
        val result: Result<NonZeroInt> = value.asNonZeroInt
        result.getOrThrow().asInt shouldEqual value
    }

    @Test
    fun int_asNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = ZeroInt.asInt.asNonZeroInt
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.assertHasAMessage()
    }
}

class NonZeroIntSerializerTest {
    private val serializer: KSerializer<NonZeroInt> = NonZeroIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NonZeroInt_as_serial_name(): Unit =
        serializer.descriptor
            .serialName shouldEqual "${Package.number}.NonZeroInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroInt.random()
        val result: String = Json.encodeToString(serializer, x)
        result shouldEqual Json.encodeToString(x.asInt)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random().asInt
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt = Json.decodeFromString(serializer, encoded)
        result.asInt shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(ZeroInt.asInt)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
