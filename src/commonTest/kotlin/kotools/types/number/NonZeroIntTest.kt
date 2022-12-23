package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NonZeroIntTest {
    companion object {
        val ranges: Set<IntRange> =
            setOf(strictlyPositiveIntRange, strictlyNegativeIntRange)
    }

    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int = ranges.random()
            .random()
        val result: Int = value.toNonZeroInt()
            .getOrThrow()
            .value
        assertEquals(value, result)
    }

    @Test
    fun int_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = ZeroInt.value.toNonZeroInt()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }

}

class NonZeroIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NonZeroInt_as_serial_name(): Unit =
        assertEquals(
            expected = "${Package.number}.NonZeroInt",
            actual = NonZeroIntSerializer.descriptor.serialName
        )

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroIntTest.ranges.random()
            .random()
            .toNonZeroInt()
            .getOrThrow()
        val result: String = Json.encodeToString(NonZeroIntSerializer, x)
        val expected: String = Json.encodeToString(x.value)
        assertEquals(expected, result)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroIntTest.ranges.random()
            .random()
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt =
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        assertEquals(value, result.value)
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(ZeroInt.value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
