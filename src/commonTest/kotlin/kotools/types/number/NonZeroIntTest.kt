package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.shouldNotEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NonZeroIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = NonZeroInt.min
        result.toInt() shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = NonZeroInt.max
        result.toInt() shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val x: NonZeroInt = NonZeroInt.random()
        x.toInt() shouldNotEqual NonZeroInt.random().toInt()
    }
}

class NonZeroIntTest {
    @Test
    fun int_div_should_pass_with_a_NonZeroInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x / y
        result shouldEqual x / y.toInt()
    }

    @Test
    fun number_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Number = NonZeroInt.random()
            .toInt()
        val result: Result<NonZeroInt> = value.toNonZeroInt()
        result.getOrThrow()
            .toInt() shouldEqual value
    }

    @Test
    fun number_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val value: Number = ZeroInt.toInt()
        val result: Result<NonZeroInt> = value.toNonZeroInt()
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
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
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt = Json.decodeFromString(serializer, encoded)
        result.toInt() shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(ZeroInt.toInt())
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        }
        exception.shouldHaveAMessage()
    }
}
